package com.linln.admin.base.controller;

import com.linln.admin.base.domain.DeviceSort;
import com.linln.admin.base.service.DeviceSortService;
import com.linln.admin.base.validator.DeviceSortValid;
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
@RequestMapping("/base/deviceSort")
public class DeviceSortController {

    @Autowired
    private DeviceSortService deviceSortService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:deviceSort:index")
    public String index(Model model, DeviceSort deviceSort) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("device_type", match -> match.contains())
                .withMatcher("device_code", match -> match.contains());

        // 获取数据列表
        Example<DeviceSort> example = Example.of(deviceSort, matcher);
        Page<DeviceSort> list = deviceSortService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/deviceSort/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:deviceSort:add")
    public String toAdd() {
        return "/base/deviceSort/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:deviceSort:edit")
    public String toEdit(@PathVariable("id") DeviceSort deviceSort, Model model) {
        model.addAttribute("deviceSort", deviceSort);
        return "/base/deviceSort/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:deviceSort:add", "base:deviceSort:edit"})
    @ResponseBody
    public ResultVo save(@Validated DeviceSortValid valid, DeviceSort deviceSort) {
        // 复制保留无需修改的数据
        if (deviceSort.getId() != null) {
            DeviceSort beDeviceSort = deviceSortService.getById(deviceSort.getId());
            EntityBeanUtil.copyProperties(beDeviceSort, deviceSort);
        }

        // 保存数据
        deviceSortService.save(deviceSort);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:deviceSort:detail")
    public String toDetail(@PathVariable("id") DeviceSort deviceSort, Model model) {
        model.addAttribute("deviceSort",deviceSort);
        return "/base/deviceSort/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:deviceSort:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (deviceSortService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}