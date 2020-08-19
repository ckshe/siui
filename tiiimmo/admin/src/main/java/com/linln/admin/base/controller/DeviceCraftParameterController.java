package com.linln.admin.base.controller;

import com.linln.admin.base.domain.DeviceCraftParameter;
import com.linln.admin.base.service.DeviceCraftParameterService;
import com.linln.admin.base.validator.DeviceCraftParameterValid;
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
 * @date 2020/08/18
 */
@Controller
@RequestMapping("/base/deviceCraftParameter")
public class DeviceCraftParameterController {

    @Autowired
    private DeviceCraftParameterService deviceCraftParameterService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:deviceCraftParameter:index")
    public String index(Model model, DeviceCraftParameter deviceCraftParameter) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("device_code", match -> match.contains())
                .withMatcher("craft", match -> match.contains());

        // 获取数据列表
        Example<DeviceCraftParameter> example = Example.of(deviceCraftParameter, matcher);
        Page<DeviceCraftParameter> list = deviceCraftParameterService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/deviceCraftParameter/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:deviceCraftParameter:add")
    public String toAdd() {
        return "/base/deviceCraftParameter/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:deviceCraftParameter:edit")
    public String toEdit(@PathVariable("id") DeviceCraftParameter deviceCraftParameter, Model model) {
        model.addAttribute("deviceCraftParameter", deviceCraftParameter);
        return "/base/deviceCraftParameter/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:deviceCraftParameter:add", "base:deviceCraftParameter:edit"})
    @ResponseBody
    public ResultVo save(@Validated DeviceCraftParameterValid valid, DeviceCraftParameter deviceCraftParameter) {
        // 复制保留无需修改的数据
        if (deviceCraftParameter.getId() != null) {
            DeviceCraftParameter beDeviceCraftParameter = deviceCraftParameterService.getById(deviceCraftParameter.getId());
            EntityBeanUtil.copyProperties(beDeviceCraftParameter, deviceCraftParameter);
        }

        // 保存数据
        deviceCraftParameterService.save(deviceCraftParameter);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:deviceCraftParameter:detail")
    public String toDetail(@PathVariable("id") DeviceCraftParameter deviceCraftParameter, Model model) {
        model.addAttribute("deviceCraftParameter",deviceCraftParameter);
        return "/base/deviceCraftParameter/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:deviceCraftParameter:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (deviceCraftParameterService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}