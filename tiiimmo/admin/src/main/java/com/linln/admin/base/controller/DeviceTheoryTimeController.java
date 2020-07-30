package com.linln.admin.base.controller;

import com.linln.admin.base.domain.DeviceTheoryTime;
import com.linln.admin.base.service.DeviceTheoryTimeService;
import com.linln.admin.base.validator.DeviceTheoryTimeValid;
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
 * @date 2020/07/30
 */
@Controller
@RequestMapping("/base/deviceTheoryTime")
public class DeviceTheoryTimeController {

    @Autowired
    private DeviceTheoryTimeService deviceTheoryTimeService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:deviceTheoryTime:index")
    public String index(Model model, DeviceTheoryTime deviceTheoryTime) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_code", match -> match.contains())
                .withMatcher("device_code", match -> match.contains());

        // 获取数据列表
        Example<DeviceTheoryTime> example = Example.of(deviceTheoryTime, matcher);
        Page<DeviceTheoryTime> list = deviceTheoryTimeService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/deviceTheoryTime/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:deviceTheoryTime:add")
    public String toAdd() {
        return "/base/deviceTheoryTime/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:deviceTheoryTime:edit")
    public String toEdit(@PathVariable("id") DeviceTheoryTime deviceTheoryTime, Model model) {
        model.addAttribute("deviceTheoryTime", deviceTheoryTime);
        return "/base/deviceTheoryTime/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:deviceTheoryTime:add", "base:deviceTheoryTime:edit"})
    @ResponseBody
    public ResultVo save(@Validated DeviceTheoryTimeValid valid, DeviceTheoryTime deviceTheoryTime) {
        // 复制保留无需修改的数据
        if (deviceTheoryTime.getId() != null) {
            DeviceTheoryTime beDeviceTheoryTime = deviceTheoryTimeService.getById(deviceTheoryTime.getId());
            EntityBeanUtil.copyProperties(beDeviceTheoryTime, deviceTheoryTime);
        }

        // 保存数据
        deviceTheoryTimeService.save(deviceTheoryTime);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:deviceTheoryTime:detail")
    public String toDetail(@PathVariable("id") DeviceTheoryTime deviceTheoryTime, Model model) {
        model.addAttribute("deviceTheoryTime",deviceTheoryTime);
        return "/base/deviceTheoryTime/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:deviceTheoryTime:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (deviceTheoryTimeService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}