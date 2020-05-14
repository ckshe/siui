package com.linln.admin.base.controller;

import com.linln.admin.base.domain.EnvironmentRecordTime;
import com.linln.admin.base.service.EnvironmentRecordTimeService;
import com.linln.admin.base.validator.EnvironmentRecordTimeValid;
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
 * @date 2020/05/13
 */
@Controller
@RequestMapping("/base/environmentRecordTime")
public class EnvironmentRecordTimeController {

    @Autowired
    private EnvironmentRecordTimeService environmentRecordTimeService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("environmentRecordTime:environmentRecordTime:index")
    public String index(Model model, EnvironmentRecordTime environmentRecordTime) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("recordDate", match -> match.contains());

        // 获取数据列表
        Example<EnvironmentRecordTime> example = Example.of(environmentRecordTime, matcher);
        Page<EnvironmentRecordTime> list = environmentRecordTimeService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/environmentRecordTime/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("environmentRecordTime:environmentRecordTime:add")
    public String toAdd() {
        return "/base/environmentRecordTime/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("environmentRecordTime:environmentRecordTime:edit")
    public String toEdit(@PathVariable("id") EnvironmentRecordTime environmentRecordTime, Model model) {
        model.addAttribute("environmentRecordTime", environmentRecordTime);
        return "/base/environmentRecordTime/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"environmentRecordTime:environmentRecordTime:add", "environmentRecordTime:environmentRecordTime:edit"})
    @ResponseBody
    public ResultVo save(@Validated EnvironmentRecordTimeValid valid, EnvironmentRecordTime environmentRecordTime) {
        // 复制保留无需修改的数据
        if (environmentRecordTime.getId() != null) {
            EnvironmentRecordTime beEnvironmentRecordTime = environmentRecordTimeService.getById(environmentRecordTime.getId());
            EntityBeanUtil.copyProperties(beEnvironmentRecordTime, environmentRecordTime);
        }

        // 保存数据
        environmentRecordTimeService.save(environmentRecordTime);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("environmentRecordTime:environmentRecordTime:detail")
    public String toDetail(@PathVariable("id") EnvironmentRecordTime environmentRecordTime, Model model) {
        model.addAttribute("environmentRecordTime",environmentRecordTime);
        return "/base/environmentRecordTime/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("environmentRecordTime:environmentRecordTime:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (environmentRecordTimeService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}