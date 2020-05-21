package com.linln.admin.project.controller;

import com.linln.admin.project.domain.MouldTechnology;
import com.linln.admin.project.service.MouldTechnologyService;
import com.linln.admin.project.validator.MouldTechnologyValid;
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
@RequestMapping("/project/mouldTechnology")
public class MouldTechnologyController {

    @Autowired
    private MouldTechnologyService mouldTechnologyService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("project:mouldTechnology:index")
    public String index(Model model, MouldTechnology mouldTechnology) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("mould_technology_code", match -> match.contains())
                .withMatcher("pcb_code", match -> match.contains());

        // 获取数据列表
        Example<MouldTechnology> example = Example.of(mouldTechnology, matcher);
        Page<MouldTechnology> list = mouldTechnologyService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/project/mouldTechnology/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("project:mouldTechnology:add")
    public String toAdd() {
        return "/project/mouldTechnology/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("project:mouldTechnology:edit")
    public String toEdit(@PathVariable("id") MouldTechnology mouldTechnology, Model model) {
        model.addAttribute("mouldTechnology", mouldTechnology);
        return "/project/mouldTechnology/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"project:mouldTechnology:add", "project:mouldTechnology:edit"})
    @ResponseBody
    public ResultVo save(@Validated MouldTechnologyValid valid, MouldTechnology mouldTechnology) {
        // 复制保留无需修改的数据
        if (mouldTechnology.getId() != null) {
            MouldTechnology beMouldTechnology = mouldTechnologyService.getById(mouldTechnology.getId());
            EntityBeanUtil.copyProperties(beMouldTechnology, mouldTechnology);
        }

        // 保存数据
        mouldTechnologyService.save(mouldTechnology);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("project:mouldTechnology:detail")
    public String toDetail(@PathVariable("id") MouldTechnology mouldTechnology, Model model) {
        model.addAttribute("mouldTechnology",mouldTechnology);
        return "/project/mouldTechnology/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("project:mouldTechnology:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (mouldTechnologyService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}