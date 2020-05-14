package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Vehicle;
import com.linln.admin.base.service.VehicleService;
import com.linln.admin.base.validator.VehicleValid;
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
 * @author 小懒虫
 * @date 2020/05/13
 */
@Controller
@RequestMapping("/base/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:vehicle:index")
    public String index(Model model, Vehicle vehicle) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("serial", match -> match.contains());

        // 获取数据列表
        Example<Vehicle> example = Example.of(vehicle, matcher);
        Page<Vehicle> list = vehicleService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/vehicle/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:vehicle:add")
    public String toAdd() {
        return "/base/vehicle/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:vehicle:edit")
    public String toEdit(@PathVariable("id") Vehicle vehicle, Model model) {
        model.addAttribute("vehicle", vehicle);
        return "/base/vehicle/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:vehicle:add", "base:vehicle:edit"})
    @ResponseBody
    public ResultVo save(@Validated VehicleValid valid, Vehicle vehicle) {
        // 复制保留无需修改的数据
        if (vehicle.getId() != null) {
            Vehicle beVehicle = vehicleService.getById(vehicle.getId());
            EntityBeanUtil.copyProperties(beVehicle, vehicle);
        }

        // 保存数据
        vehicleService.save(vehicle);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:vehicle:detail")
    public String toDetail(@PathVariable("id") Vehicle vehicle, Model model) {
        model.addAttribute("vehicle",vehicle);
        return "/base/vehicle/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:vehicle:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (vehicleService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}