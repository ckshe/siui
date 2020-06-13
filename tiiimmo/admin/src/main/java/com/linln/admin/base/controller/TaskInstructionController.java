package com.linln.admin.base.controller;

import com.linln.admin.base.domain.TaskInstruction;
import com.linln.admin.base.service.TaskInstructionService;
import com.linln.admin.base.validator.TaskInstructionValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.shiro.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author 连
 * @date 2020/06/09
 */
@Controller
@RequestMapping("/base/taskInstruction")
public class TaskInstructionController {

    @Autowired
    private TaskInstructionService taskInstructionService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:taskInstructio:index")
    public String index(Model model, TaskInstruction taskInstruction) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<TaskInstruction> example = Example.of(taskInstruction, matcher);
        Page<TaskInstruction> list = taskInstructionService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/taskInstruction/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:taskInstruction:add")
    public String toAdd() {
        return "/base/taskInstruction/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:taskInstruction:edit")
    public String toEdit(@PathVariable("id") TaskInstruction taskInstruction, Model model) {
        model.addAttribute("taskInstruction", taskInstruction);
        return "/base/taskInstruction/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:taskInstruction:add", "base:taskInstruction:edit"})
    @ResponseBody
    public ResultVo save(@Validated TaskInstructionValid valid, TaskInstruction taskInstruction) {
        // 复制保留无需修改的数据
        if (taskInstruction.getId() != null) {
            TaskInstruction beTaskInstruction = taskInstructionService.getById(taskInstruction.getId());
            EntityBeanUtil.copyProperties(beTaskInstruction, taskInstruction);
        }

        taskInstruction.setUploadTime(new Date());
        taskInstruction.setUploadPeople(ShiroUtil.getSubject().getUsername());
        // 保存数据
        taskInstructionService.save(taskInstruction);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:taskInstruction:detail")
    public String toDetail(@PathVariable("id") TaskInstruction taskInstruction, Model model) {
        model.addAttribute("taskInstruction",taskInstruction);
        return "/base/taskInstruction/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:taskInstruction:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (taskInstructionService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}