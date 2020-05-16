package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.TaskSheet;
import com.linln.admin.produce.service.TaskSheetService;
import com.linln.admin.produce.validator.TaskSheetValid;
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
 * @author 小懒虫
 * @date 2020/05/16
 */
@Controller
@RequestMapping("/produce/taskSheet")
public class TaskSheetController {

    @Autowired
    private TaskSheetService taskSheetService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("produce:taskSheet:index")
    public String index(Model model, TaskSheet taskSheet) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("task_sheet_id", match -> match.contains());

        // 获取数据列表
        Example<TaskSheet> example = Example.of(taskSheet, matcher);
        Page<TaskSheet> list = taskSheetService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/produce/taskSheet/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("produce:taskSheet:add")
    public String toAdd() {
        return "/produce/taskSheet/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("produce:taskSheet:edit")
    public String toEdit(@PathVariable("id") TaskSheet taskSheet, Model model) {
        model.addAttribute("taskSheet", taskSheet);
        return "/produce/taskSheet/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"produce:taskSheet:add", "produce:taskSheet:edit"})
    @ResponseBody
    public ResultVo save(@Validated TaskSheetValid valid, TaskSheet taskSheet) {
        // 复制保留无需修改的数据
        if (taskSheet.getId() != null) {
            TaskSheet beTaskSheet = taskSheetService.getById(taskSheet.getId());
            EntityBeanUtil.copyProperties(beTaskSheet, taskSheet);
        }

        // 保存数据
        taskSheetService.save(taskSheet);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("produce:taskSheet:detail")
    public String toDetail(@PathVariable("id") TaskSheet taskSheet, Model model) {
        model.addAttribute("taskSheet",taskSheet);
        return "/produce/taskSheet/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("produce:taskSheet:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (taskSheetService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}