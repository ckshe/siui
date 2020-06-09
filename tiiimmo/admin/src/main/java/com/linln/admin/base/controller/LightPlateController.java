package com.linln.admin.base.controller;

import com.linln.admin.base.domain.LightPlate;
import com.linln.admin.base.service.LightPlateService;
import com.linln.admin.base.validator.LightPlateValid;
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
 * @author 连
 * @date 2020/06/09
 */
@Controller
@RequestMapping("/base/lightPlate")
public class LightPlateController {

    @Autowired
    private LightPlateService lightPlateService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:lightPlate:index")
    public String index(Model model, LightPlate lightPlate) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<LightPlate> example = Example.of(lightPlate, matcher);
        Page<LightPlate> list = lightPlateService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/lightPlate/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:lightPlate:add")
    public String toAdd() {
        return "/base/lightPlate/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:lightPlate:edit")
    public String toEdit(@PathVariable("id") LightPlate lightPlate, Model model) {
        model.addAttribute("lightPlate", lightPlate);
        return "/base/lightPlate/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:lightPlate:add", "base:lightPlate:edit"})
    @ResponseBody
    public ResultVo save(@Validated LightPlateValid valid, LightPlate lightPlate) {
        // 复制保留无需修改的数据
        if (lightPlate.getId() != null) {
            LightPlate beLightPlate = lightPlateService.getById(lightPlate.getId());
            EntityBeanUtil.copyProperties(beLightPlate, lightPlate);
        }

        // 保存数据
        lightPlateService.save(lightPlate);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:lightPlate:detail")
    public String toDetail(@PathVariable("id") LightPlate lightPlate, Model model) {
        model.addAttribute("lightPlate",lightPlate);
        return "/base/lightPlate/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:lightPlate:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (lightPlateService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}