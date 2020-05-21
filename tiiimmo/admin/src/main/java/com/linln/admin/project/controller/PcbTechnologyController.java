package com.linln.admin.project.controller;

import com.linln.admin.project.domain.PcbTechnology;
import com.linln.admin.project.service.PcbTechnologyService;
import com.linln.admin.project.validator.PcbTechnologyValid;
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
@RequestMapping("/project/pcbTechnology")
public class PcbTechnologyController {

    @Autowired
    private PcbTechnologyService pcbTechnologyService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("project:pcbTechnology:index")
    public String index(Model model, PcbTechnology pcbTechnology) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_technology_code", match -> match.contains())
                .withMatcher("pcb_code", match -> match.contains());

        // 获取数据列表
        Example<PcbTechnology> example = Example.of(pcbTechnology, matcher);
        Page<PcbTechnology> list = pcbTechnologyService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/project/pcbTechnology/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("project:pcbTechnology:add")
    public String toAdd() {
        return "/project/pcbTechnology/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("project:pcbTechnology:edit")
    public String toEdit(@PathVariable("id") PcbTechnology pcbTechnology, Model model) {
        model.addAttribute("pcbTechnology", pcbTechnology);
        return "/project/pcbTechnology/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"project:pcbTechnology:add", "project:pcbTechnology:edit"})
    @ResponseBody
    public ResultVo save(@Validated PcbTechnologyValid valid, PcbTechnology pcbTechnology) {
        // 复制保留无需修改的数据
        if (pcbTechnology.getId() != null) {
            PcbTechnology bePcbTechnology = pcbTechnologyService.getById(pcbTechnology.getId());
            EntityBeanUtil.copyProperties(bePcbTechnology, pcbTechnology);
        }

        // 保存数据
        pcbTechnologyService.save(pcbTechnology);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("project:pcbTechnology:detail")
    public String toDetail(@PathVariable("id") PcbTechnology pcbTechnology, Model model) {
        model.addAttribute("pcbTechnology",pcbTechnology);
        return "/project/pcbTechnology/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("project:pcbTechnology:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (pcbTechnologyService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}