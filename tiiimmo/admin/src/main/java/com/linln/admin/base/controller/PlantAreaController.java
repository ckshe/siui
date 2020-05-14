package com.linln.admin.base.controller;

import com.linln.admin.base.domain.PlantArea;
import com.linln.admin.base.service.PlantAreaService;
import com.linln.admin.base.validator.PlantAreaValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author www
 * @date 2020/05/14
 */
@Controller
@RequestMapping("/base/plantArea")
public class PlantAreaController {

    @Autowired
    private PlantAreaService plantAreaService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:plantArea:index")
    public String index(Model model, PlantArea plantArea) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<PlantArea> example = Example.of(plantArea, matcher);
        Page<PlantArea> list = plantAreaService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/plantArea/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:plantArea:add")
    public String toAdd() {
        return "/base/plantArea/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:plantArea:edit")
    public String toEdit(@PathVariable("id") PlantArea plantArea, Model model) {
        model.addAttribute("plantArea", plantArea);
        return "/base/plantArea/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:plantArea:add", "base:plantArea:edit"})
    @ResponseBody
    public ResultVo save(@Validated PlantAreaValid valid, PlantArea plantArea) {
        // 复制保留无需修改的数据
        if (plantArea.getId() != null) {
            PlantArea bePlantArea = plantAreaService.getById(plantArea.getId());
            EntityBeanUtil.copyProperties(bePlantArea, plantArea);
        }

        // 保存数据
        plantAreaService.save(plantArea);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:plantArea:detail")
    public String toDetail(@PathVariable("id") PlantArea plantArea, Model model) {
        model.addAttribute("plantArea",plantArea);
        return "/base/plantArea/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:plantArea:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (plantAreaService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}