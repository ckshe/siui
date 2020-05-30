package com.linln.admin.base.controller;

import com.linln.admin.base.domain.DeviceTechnology;
import com.linln.admin.base.service.DeviceTechnologyService;
import com.linln.admin.base.validator.DeviceTechnologyValid;
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
@RequestMapping("/base/deviceTechnology")
public class DeviceTechnologyController {

    @Autowired
    private DeviceTechnologyService deviceTechnologyService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:deviceTechnology:index")
    public String index(Model model, DeviceTechnology deviceTechnology) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("param_code", match -> match.contains())
                .withMatcher("device_code", match -> match.contains())
                .withMatcher("key_technology_name", match -> match.contains());

        // 获取数据列表
        Example<DeviceTechnology> example = Example.of(deviceTechnology, matcher);
        Page<DeviceTechnology> list = deviceTechnologyService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/deviceTechnology/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:deviceTechnology:add")
    public String toAdd() {
        return "/base/deviceTechnology/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:deviceTechnology:edit")
    public String toEdit(@PathVariable("id") DeviceTechnology deviceTechnology, Model model) {
        model.addAttribute("deviceTechnology", deviceTechnology);
        return "/base/deviceTechnology/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:deviceTechnology:add", "base:deviceTechnology:edit"})
    @ResponseBody
    public ResultVo save(@Validated DeviceTechnologyValid valid, DeviceTechnology deviceTechnology) {
        // 复制保留无需修改的数据
        if (deviceTechnology.getId() != null) {
            DeviceTechnology beDeviceTechnology = deviceTechnologyService.getById(deviceTechnology.getId());
            EntityBeanUtil.copyProperties(beDeviceTechnology, deviceTechnology);
        }

        // 保存数据
        deviceTechnologyService.save(deviceTechnology);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:deviceTechnology:detail")
    public String toDetail(@PathVariable("id") DeviceTechnology deviceTechnology, Model model) {
        model.addAttribute("deviceTechnology",deviceTechnology);
        return "/base/deviceTechnology/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:deviceTechnology:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (deviceTechnologyService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}