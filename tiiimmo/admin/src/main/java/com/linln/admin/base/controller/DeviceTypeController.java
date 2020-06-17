package com.linln.admin.base.controller;

import com.linln.admin.base.domain.DeviceType;
import com.linln.admin.base.domain.Line;
import com.linln.admin.base.service.DeviceTypeService;
import com.linln.admin.base.validator.DeviceTypeValid;
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
@RequestMapping("/base/deviceType")
public class DeviceTypeController {

    @Autowired
    private DeviceTypeService deviceTypeService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("device:deviceType:index")
    public String index(Model model, DeviceType deviceType) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<DeviceType> example = Example.of(deviceType, matcher);
        Page<DeviceType> list = deviceTypeService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/deviceType/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("device:deviceType:add")
    public String toAdd() {
        return "/base/deviceType/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("device:deviceType:edit")
    public String toEdit(@PathVariable("id") DeviceType deviceType, Model model) {
        model.addAttribute("deviceType", deviceType);
        return "/base/deviceType/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"device:deviceType:add", "device:deviceType:edit"})
    @ResponseBody
    public ResultVo save(@Validated DeviceTypeValid valid, DeviceType deviceType) {
        // 复制保留无需修改的数据
        if (deviceType.getId() != null) {
            DeviceType beDeviceType = deviceTypeService.getById(deviceType.getId());
            EntityBeanUtil.copyProperties(beDeviceType, deviceType);
        }

        // 保存数据
        deviceTypeService.save(deviceType);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("device:deviceType:detail")
    public String toDetail(@PathVariable("id") DeviceType deviceType, Model model) {
        model.addAttribute("deviceType",deviceType);
        return "/base/deviceType/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("device:deviceType:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (deviceTypeService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    @GetMapping("/findDeviceType")
    @ResponseBody
    public ResultVo findDeviceType(){
        List<DeviceType> deviceTypes = deviceTypeService.list();
        if (deviceTypes!= null){
            return ResultVoUtil.success("查询成功",deviceTypes);
        } else {
            return ResultVoUtil.error(400,"查询失败");
        }

    }
}