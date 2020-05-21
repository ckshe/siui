package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.MaintenanceTable;
import com.linln.admin.maintenance.service.MaintenanceTableService;
import com.linln.admin.maintenance.validator.MaintenanceTableValid;
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
@RequestMapping("/maintenance/maintenanceTable")
public class MaintenanceTableController {

    @Autowired
    private MaintenanceTableService maintenanceTableService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:maintenanceTable:index")
    public String index(Model model, MaintenanceTable maintenanceTable) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<MaintenanceTable> example = Example.of(maintenanceTable, matcher);
        Page<MaintenanceTable> list = maintenanceTableService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/maintenanceTable/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:maintenanceTable:add")
    public String toAdd() {
        return "/maintenance/maintenanceTable/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:maintenanceTable:edit")
    public String toEdit(@PathVariable("id") MaintenanceTable maintenanceTable, Model model) {
        model.addAttribute("maintenanceTable", maintenanceTable);
        return "/maintenance/maintenanceTable/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:maintenanceTable:add", "maintenance:maintenanceTable:edit"})
    @ResponseBody
    public ResultVo save(@Validated MaintenanceTableValid valid, MaintenanceTable maintenanceTable) {
        // 复制保留无需修改的数据
        if (maintenanceTable.getId() != null) {
            MaintenanceTable beMaintenanceTable = maintenanceTableService.getById(maintenanceTable.getId());
            EntityBeanUtil.copyProperties(beMaintenanceTable, maintenanceTable);
        }

        // 保存数据
        maintenanceTableService.save(maintenanceTable);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:maintenanceTable:detail")
    public String toDetail(@PathVariable("id") MaintenanceTable maintenanceTable, Model model) {
        model.addAttribute("maintenanceTable",maintenanceTable);
        return "/maintenance/maintenanceTable/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:maintenanceTable:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (maintenanceTableService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}