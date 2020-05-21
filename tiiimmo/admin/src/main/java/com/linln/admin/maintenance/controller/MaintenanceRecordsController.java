package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.MaintenanceRecords;
import com.linln.admin.maintenance.service.MaintenanceRecordsService;
import com.linln.admin.maintenance.validator.MaintenanceRecordsValid;
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
@RequestMapping("/maintenance/maintenanceRecords")
public class MaintenanceRecordsController {

    @Autowired
    private MaintenanceRecordsService maintenanceRecordsService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:maintenanceRecords:index")
    public String index(Model model, MaintenanceRecords maintenanceRecords) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("deviceCode", match -> match.contains());

        // 获取数据列表
        Example<MaintenanceRecords> example = Example.of(maintenanceRecords, matcher);
        Page<MaintenanceRecords> list = maintenanceRecordsService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/maintenanceRecords/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:maintenanceRecords:add")
    public String toAdd() {
        return "/maintenance/maintenanceRecords/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:maintenanceRecords:edit")
    public String toEdit(@PathVariable("id") MaintenanceRecords maintenanceRecords, Model model) {
        model.addAttribute("maintenanceRecords", maintenanceRecords);
        return "/maintenance/maintenanceRecords/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:maintenanceRecords:add", "maintenance:maintenanceRecords:edit"})
    @ResponseBody
    public ResultVo save(@Validated MaintenanceRecordsValid valid, MaintenanceRecords maintenanceRecords) {
        // 复制保留无需修改的数据
        if (maintenanceRecords.getId() != null) {
            MaintenanceRecords beMaintenanceRecords = maintenanceRecordsService.getById(maintenanceRecords.getId());
            EntityBeanUtil.copyProperties(beMaintenanceRecords, maintenanceRecords);
        }

        // 保存数据
        maintenanceRecordsService.save(maintenanceRecords);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:maintenanceRecords:detail")
    public String toDetail(@PathVariable("id") MaintenanceRecords maintenanceRecords, Model model) {
        model.addAttribute("maintenanceRecords",maintenanceRecords);
        return "/maintenance/maintenanceRecords/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:maintenanceRecords:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (maintenanceRecordsService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}