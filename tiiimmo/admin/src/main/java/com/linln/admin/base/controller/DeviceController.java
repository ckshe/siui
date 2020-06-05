package com.linln.admin.base.controller;

import com.linln.RespAndReqs.DeviceReq;
import com.linln.admin.base.domain.Device;
import com.linln.admin.base.service.DeviceService;
import com.linln.admin.base.validator.DeviceValid;
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
@RequestMapping("/base/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:device:index")
    public String index(Model model, Device device) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("belong_plant_area", match -> match.contains())
                .withMatcher("belong_line", match -> match.contains())
                .withMatcher("device_type", match -> match.contains())
                .withMatcher("device_code", match -> match.contains())
                .withMatcher("device_name", match -> match.contains())
                .withMatcher("mac", match -> match.contains());

        // 获取数据列表
        Example<Device> example = Example.of(device, matcher);
        Page<Device> list = deviceService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/device/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:device:add")
    public String toAdd() {
        return "/base/device/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:device:edit")
    public String toEdit(@PathVariable("id") Device device, Model model) {
        model.addAttribute("device", device);
        return "/base/device/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:device:add", "base:device:edit"})
    @ResponseBody
    public ResultVo save(@Validated DeviceValid valid, Device device) {
        // 复制保留无需修改的数据
        if (device.getId() != null) {
            Device beDevice = deviceService.getById(device.getId());
            EntityBeanUtil.copyProperties(beDevice, device);
        }

        // 保存数据
        deviceService.save(device);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:device:detail")
    public String toDetail(@PathVariable("" +
            "id") Device device, Model model) {
        model.addAttribute("device",device);
        return "/base/device/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:device:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (deviceService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    //根据工序名称选择机台
    @PostMapping("/getDeviceByProcess")
    //@RequiresPermissions("base:device:status")
    @ResponseBody

    //暂时所有设备可选
    public ResultVo getDeviceByProcess(@RequestBody DeviceReq req){


        return deviceService.getDeviceByProcess(req);
    }
}