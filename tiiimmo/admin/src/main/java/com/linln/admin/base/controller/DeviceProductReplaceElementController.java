package com.linln.admin.base.controller;

import com.linln.admin.base.domain.DeviceProductReplaceElement;
import com.linln.admin.base.service.DeviceProductReplaceElementService;
import com.linln.admin.base.validator.DeviceProductReplaceElementValid;
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
 * @date 2020/08/01
 */
@Controller
@RequestMapping("/base/deviceProductReplaceElement")
public class DeviceProductReplaceElementController {

    @Autowired
    private DeviceProductReplaceElementService deviceProductReplaceElementService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:deviceProductReplaceElement:index")
    public String index(Model model, DeviceProductReplaceElement deviceProductReplaceElement) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_code", match -> match.contains())
                .withMatcher("original_product_name", match -> match.contains())
                .withMatcher("replace_product_name", match -> match.contains());

        // 获取数据列表
        Example<DeviceProductReplaceElement> example = Example.of(deviceProductReplaceElement, matcher);
        Page<DeviceProductReplaceElement> list = deviceProductReplaceElementService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/deviceProductReplaceElement/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:deviceProductReplaceElement:add")
    public String toAdd() {
        return "/base/deviceProductReplaceElement/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:deviceProductReplaceElement:edit")
    public String toEdit(@PathVariable("id") DeviceProductReplaceElement deviceProductReplaceElement, Model model) {
        model.addAttribute("deviceProductReplaceElement", deviceProductReplaceElement);
        return "/base/deviceProductReplaceElement/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:deviceProductReplaceElement:add", "base:deviceProductReplaceElement:edit"})
    @ResponseBody
    public ResultVo save(@Validated DeviceProductReplaceElementValid valid, DeviceProductReplaceElement deviceProductReplaceElement) {
        // 复制保留无需修改的数据
       /* if (deviceProductReplaceElement.getId() != null) {
            DeviceProductReplaceElement beDeviceProductReplaceElement = deviceProductReplaceElementService.getById(deviceProductReplaceElement.getId());
            EntityBeanUtil.copyProperties(beDeviceProductReplaceElement, deviceProductReplaceElement);
        }*/

        // 保存数据
        deviceProductReplaceElementService.save(deviceProductReplaceElement);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:deviceProductReplaceElement:detail")
    public String toDetail(@PathVariable("id") DeviceProductReplaceElement deviceProductReplaceElement, Model model) {
        model.addAttribute("deviceProductReplaceElement",deviceProductReplaceElement);
        return "/base/deviceProductReplaceElement/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:deviceProductReplaceElement:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (deviceProductReplaceElementService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}