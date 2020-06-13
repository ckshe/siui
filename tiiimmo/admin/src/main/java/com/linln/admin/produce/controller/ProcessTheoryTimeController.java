package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.ProcessTheoryTime;
import com.linln.admin.produce.service.ProcessTheoryTimeService;
import com.linln.admin.produce.validator.ProcessTheoryTimeValid;
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
 * @date 2020/06/13
 */
@Controller
@RequestMapping("/produce/processTheoryTime")
public class ProcessTheoryTimeController {

    @Autowired
    private ProcessTheoryTimeService processTheoryTimeService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("produce:processTheoryTime:index")
    public String index(Model model, ProcessTheoryTime processTheoryTime) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_code", match -> match.contains())
                .withMatcher("process_name", match -> match.contains());

        // 获取数据列表
        Example<ProcessTheoryTime> example = Example.of(processTheoryTime, matcher);
        Page<ProcessTheoryTime> list = processTheoryTimeService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/produce/processTheoryTime/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("produce:processTheoryTime:add")
    public String toAdd() {
        return "/produce/processTheoryTime/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("produce:processTheoryTime:edit")
    public String toEdit(@PathVariable("id") ProcessTheoryTime processTheoryTime, Model model) {
        model.addAttribute("processTheoryTime", processTheoryTime);
        return "/produce/processTheoryTime/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"produce:processTheoryTime:add", "produce:processTheoryTime:edit"})
    @ResponseBody
    public ResultVo save(@Validated ProcessTheoryTimeValid valid, ProcessTheoryTime processTheoryTime) {
        // 复制保留无需修改的数据
        if (processTheoryTime.getId() != null) {
            ProcessTheoryTime beProcessTheoryTime = processTheoryTimeService.getById(processTheoryTime.getId());
            EntityBeanUtil.copyProperties(beProcessTheoryTime, processTheoryTime);
        }

        // 保存数据
        processTheoryTimeService.save(processTheoryTime);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("produce:processTheoryTime:detail")
    public String toDetail(@PathVariable("id") ProcessTheoryTime processTheoryTime, Model model) {
        model.addAttribute("processTheoryTime",processTheoryTime);
        return "/produce/processTheoryTime/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("produce:processTheoryTime:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (processTheoryTimeService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}