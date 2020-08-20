package com.linln.admin.base.controller;

import com.linln.admin.base.domain.ProcessTaskDeviceTheorytime;
import com.linln.admin.base.service.ProcessTaskDeviceTheorytimeService;
import com.linln.admin.base.validator.ProcessTaskDeviceTheorytimeValid;
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
 * @author lian
 * @date 2020/08/20
 */
@Controller
@RequestMapping("/base/processTaskDeviceTheorytime")
public class ProcessTaskDeviceTheorytimeController {

    @Autowired
    private ProcessTaskDeviceTheorytimeService processTaskDeviceTheorytimeService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:processTaskDeviceTheorytime:index")
    public String index(Model model, ProcessTaskDeviceTheorytime processTaskDeviceTheorytime) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("deviceCode", match -> match.contains())
                .withMatcher("pcbCode", match -> match.contains())
                .withMatcher("processTaskCode", match -> match.contains());

        // 获取数据列表
        Example<ProcessTaskDeviceTheorytime> example = Example.of(processTaskDeviceTheorytime, matcher);
        Page<ProcessTaskDeviceTheorytime> list = processTaskDeviceTheorytimeService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/processTaskDeviceTheorytime/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:processTaskDeviceTheorytime:add")
    public String toAdd() {
        return "/base/processTaskDeviceTheorytime/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:processTaskDeviceTheorytime:edit")
    public String toEdit(@PathVariable("id") ProcessTaskDeviceTheorytime processTaskDeviceTheorytime, Model model) {
        model.addAttribute("processTaskDeviceTheorytime", processTaskDeviceTheorytime);
        return "/base/processTaskDeviceTheorytime/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:processTaskDeviceTheorytime:add", "base:processTaskDeviceTheorytime:edit"})
    @ResponseBody
    public ResultVo save(@Validated ProcessTaskDeviceTheorytimeValid valid, ProcessTaskDeviceTheorytime processTaskDeviceTheorytime) {
        // 复制保留无需修改的数据
        if (processTaskDeviceTheorytime.getId() != null) {
            ProcessTaskDeviceTheorytime beProcessTaskDeviceTheorytime = processTaskDeviceTheorytimeService.getById(processTaskDeviceTheorytime.getId());
            EntityBeanUtil.copyProperties(beProcessTaskDeviceTheorytime, processTaskDeviceTheorytime);
        }

        // 保存数据
        processTaskDeviceTheorytimeService.save(processTaskDeviceTheorytime);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:processTaskDeviceTheorytime:detail")
    public String toDetail(@PathVariable("id") ProcessTaskDeviceTheorytime processTaskDeviceTheorytime, Model model) {
        model.addAttribute("processTaskDeviceTheorytime",processTaskDeviceTheorytime);
        return "/base/processTaskDeviceTheorytime/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:processTaskDeviceTheorytime:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (processTaskDeviceTheorytimeService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}