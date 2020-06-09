package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Program;
import com.linln.admin.base.service.ProgramService;
import com.linln.admin.base.validator.ProgramValid;
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
 * @author 连
 * @date 2020/06/09
 */
@Controller
@RequestMapping("/base/program")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:program:index")
    public String index(Model model, Program program) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Program> example = Example.of(program, matcher);
        Page<Program> list = programService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/program/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:program:add")
    public String toAdd() {
        return "/base/program/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:program:edit")
    public String toEdit(@PathVariable("id") Program program, Model model) {
        model.addAttribute("program", program);
        return "/base/program/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:program:add", "base:program:edit"})
    @ResponseBody
    public ResultVo save(@Validated ProgramValid valid, Program program) {
        // 复制保留无需修改的数据
        if (program.getId() != null) {
            Program beProgram = programService.getById(program.getId());
            EntityBeanUtil.copyProperties(beProgram, program);
        }

        // 保存数据
        programService.save(program);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:program:detail")
    public String toDetail(@PathVariable("id") Program program, Model model) {
        model.addAttribute("program",program);
        return "/base/program/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:program:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (programService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}