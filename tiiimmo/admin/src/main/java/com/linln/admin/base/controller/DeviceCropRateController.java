package com.linln.admin.base.controller;

import com.linln.admin.base.domain.DeviceCropRate;
import com.linln.admin.base.service.DeviceCropRateService;
import com.linln.admin.base.validator.DeviceCropRateValid;
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
 * @author ww
 * @date 2020/06/19
 */
@Controller
@RequestMapping("/base/deviceCropRate")
public class DeviceCropRateController {

    @Autowired
    private DeviceCropRateService deviceCropRateService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:deviceCropRate:index")
    public String index(Model model, DeviceCropRate deviceCropRate) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("device_code", match -> match.contains());

        // 获取数据列表
        Example<DeviceCropRate> example = Example.of(deviceCropRate, matcher);
        Page<DeviceCropRate> list = deviceCropRateService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/deviceCropRate/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:deviceCropRate:add")
    public String toAdd() {
        return "/base/deviceCropRate/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:deviceCropRate:edit")
    public String toEdit(@PathVariable("id") DeviceCropRate deviceCropRate, Model model) {
        model.addAttribute("deviceCropRate", deviceCropRate);
        return "/base/deviceCropRate/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:deviceCropRate:add", "base:deviceCropRate:edit"})
    @ResponseBody
    public ResultVo save(@Validated DeviceCropRateValid valid, DeviceCropRate deviceCropRate) {
        // 复制保留无需修改的数据
        if (deviceCropRate.getId() != null) {
            DeviceCropRate beDeviceCropRate = deviceCropRateService.getById(deviceCropRate.getId());
            EntityBeanUtil.copyProperties(beDeviceCropRate, deviceCropRate);
        }

        // 保存数据
        deviceCropRateService.save(deviceCropRate);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:deviceCropRate:detail")
    public String toDetail(@PathVariable("id") DeviceCropRate deviceCropRate, Model model) {
        model.addAttribute("deviceCropRate",deviceCropRate);
        return "/base/deviceCropRate/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:deviceCropRate:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (deviceCropRateService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}