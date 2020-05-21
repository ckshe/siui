package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.PlasticBad;
import com.linln.admin.produce.service.PlasticBadService;
import com.linln.admin.produce.validator.PlasticBadValid;
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
 * @date 2020/05/21
 */
@Controller
@RequestMapping("/produce/plasticBad")
public class PlasticBadController {

    @Autowired
    private PlasticBadService plasticBadService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("produce:plasticBad:index")
    public String index(Model model, PlasticBad plasticBad) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_code", match -> match.contains())
                .withMatcher("pacth_device", match -> match.contains());

        // 获取数据列表
        Example<PlasticBad> example = Example.of(plasticBad, matcher);
        Page<PlasticBad> list = plasticBadService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/produce/plasticBad/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("produce:plasticBad:add")
    public String toAdd() {
        return "/produce/plasticBad/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("produce:plasticBad:edit")
    public String toEdit(@PathVariable("id") PlasticBad plasticBad, Model model) {
        model.addAttribute("plasticBad", plasticBad);
        return "/produce/plasticBad/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"produce:plasticBad:add", "produce:plasticBad:edit"})
    @ResponseBody
    public ResultVo save(@Validated PlasticBadValid valid, PlasticBad plasticBad) {
        // 复制保留无需修改的数据
        if (plasticBad.getId() != null) {
            PlasticBad bePlasticBad = plasticBadService.getById(plasticBad.getId());
            EntityBeanUtil.copyProperties(bePlasticBad, plasticBad);
        }

        // 保存数据
        plasticBadService.save(plasticBad);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("produce:plasticBad:detail")
    public String toDetail(@PathVariable("id") PlasticBad plasticBad, Model model) {
        model.addAttribute("plasticBad",plasticBad);
        return "/produce/plasticBad/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("produce:plasticBad:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (plasticBadService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}