package com.linln.admin.base.controller;

import com.linln.admin.base.domain.DeviceProductElement;
import com.linln.admin.base.service.DeviceProductElementService;
import com.linln.admin.base.validator.DeviceProductElementValid;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ww
 * @date 2020/06/17
 */
@Controller
@RequestMapping("/base/deviceProductElement")
public class DeviceProductElementController {

    @Autowired
    private DeviceProductElementService deviceProductElementService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:deviceProductElement:index")
    public String index(Model model, DeviceProductElement deviceProductElement) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("sample_name", match -> match.contains());

        // 获取数据列表
        Example<DeviceProductElement> example = Example.of(deviceProductElement, matcher);
        Page<DeviceProductElement> list = deviceProductElementService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/deviceProductElement/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:deviceProductElement:add")
    public String toAdd() {
        return "/base/deviceProductElement/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:deviceProductElement:edit")
    public String toEdit(@PathVariable("id") DeviceProductElement deviceProductElement, Model model) {
        model.addAttribute("deviceProductElement", deviceProductElement);
        return "/base/deviceProductElement/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:deviceProductElement:add", "base:deviceProductElement:edit"})
    @ResponseBody
    public ResultVo save(@Validated DeviceProductElementValid valid, DeviceProductElement deviceProductElement) {
        // 复制保留无需修改的数据
        if (deviceProductElement.getId() != null) {
            DeviceProductElement beDeviceProductElement = deviceProductElementService.getById(deviceProductElement.getId());
            EntityBeanUtil.copyProperties(beDeviceProductElement, deviceProductElement);
        }

        // 保存数据
        deviceProductElementService.save(deviceProductElement);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:deviceProductElement:detail")
    public String toDetail(@PathVariable("id") DeviceProductElement deviceProductElement, Model model) {
        model.addAttribute("deviceProductElement",deviceProductElement);
        return "/base/deviceProductElement/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:deviceProductElement:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (deviceProductElementService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }


    @PostMapping("/importCommonExcel")
    @ResponseBody
    //@RequiresPermissions("importCommonExcel")
    public ResultVo importCommonExcel(@RequestParam(value = "file") MultipartFile file)
    {
        try {
            String fileName = file.getOriginalFilename();
            if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                return ResultVoUtil.error("上传文件格式不正确");
            }
            return deviceProductElementService.importCommonExcel(file);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVoUtil.error("Excel导入失败");
        }
    }
}