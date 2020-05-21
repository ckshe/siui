package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.MaintenanceType;
import com.linln.admin.maintenance.service.MaintenanceTypeService;
import com.linln.admin.maintenance.validator.MaintenanceTypeValid;
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
@RequestMapping("/maintenance/maintenanceType")
public class MaintenanceTypeController {

    @Autowired
    private MaintenanceTypeService maintenanceTypeService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:maintenanceType:index")
    public String index(Model model, MaintenanceType maintenanceType) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<MaintenanceType> example = Example.of(maintenanceType, matcher);
        Page<MaintenanceType> list = maintenanceTypeService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/maintenanceType/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:maintenanceType:add")
    public String toAdd() {
        return "/maintenance/maintenanceType/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:maintenanceType:edit")
    public String toEdit(@PathVariable("id") MaintenanceType maintenanceType, Model model) {
        model.addAttribute("maintenanceType", maintenanceType);
        return "/maintenance/maintenanceType/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:maintenanceType:add", "maintenance:maintenanceType:edit"})
    @ResponseBody
    public ResultVo save(@Validated MaintenanceTypeValid valid, MaintenanceType maintenanceType) {
        // 复制保留无需修改的数据
        if (maintenanceType.getId() != null) {
            MaintenanceType beMaintenanceType = maintenanceTypeService.getById(maintenanceType.getId());
            EntityBeanUtil.copyProperties(beMaintenanceType, maintenanceType);
        }

        // 保存数据
        maintenanceTypeService.save(maintenanceType);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:maintenanceType:detail")
    public String toDetail(@PathVariable("id") MaintenanceType maintenanceType, Model model) {
        model.addAttribute("maintenanceType",maintenanceType);
        return "/maintenance/maintenanceType/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:maintenanceType:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (maintenanceTypeService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}