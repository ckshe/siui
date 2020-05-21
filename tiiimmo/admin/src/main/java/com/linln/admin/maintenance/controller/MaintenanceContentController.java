package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.MaintenanceContent;
import com.linln.admin.maintenance.service.MaintenanceContentService;
import com.linln.admin.maintenance.validator.MaintenanceContentValid;
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
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Controller
@RequestMapping("/maintenance/maintenanceContent")
public class MaintenanceContentController {

    @Autowired
    private MaintenanceContentService maintenanceContentService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:maintenanceContent:index")
    public String index(Model model, MaintenanceContent maintenanceContent) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("deviceType", match -> match.contains());

        // 获取数据列表
        Example<MaintenanceContent> example = Example.of(maintenanceContent, matcher);
        Page<MaintenanceContent> list = maintenanceContentService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/maintenanceContent/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:maintenanceContent:add")
    public String toAdd() {
        return "/maintenance/maintenanceContent/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:maintenanceContent:edit")
    public String toEdit(@PathVariable("id") MaintenanceContent maintenanceContent, Model model) {
        model.addAttribute("maintenanceContent", maintenanceContent);
        return "/maintenance/maintenanceContent/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:maintenanceContent:add", "maintenance:maintenanceContent:edit"})
    @ResponseBody
    public ResultVo save(@Validated MaintenanceContentValid valid, MaintenanceContent maintenanceContent) {
        // 复制保留无需修改的数据
        if (maintenanceContent.getId() != null) {
            MaintenanceContent beMaintenanceContent = maintenanceContentService.getById(maintenanceContent.getId());
            EntityBeanUtil.copyProperties(beMaintenanceContent, maintenanceContent);
        }

        // 保存数据
        maintenanceContentService.save(maintenanceContent);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:maintenanceContent:detail")
    public String toDetail(@PathVariable("id") MaintenanceContent maintenanceContent, Model model) {
        model.addAttribute("maintenanceContent",maintenanceContent);
        return "/maintenance/maintenanceContent/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:maintenanceContent:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (maintenanceContentService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}