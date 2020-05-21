package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.TestTool;
import com.linln.admin.maintenance.service.TestToolService;
import com.linln.admin.maintenance.validator.TestToolValid;
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
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Controller
@RequestMapping("/maintenance/testTool")
public class TestToolController {

    @Autowired
    private TestToolService testToolService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:testTool:index")
    public String index(Model model, TestTool testTool) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("toolCode", match -> match.contains());

        // 获取数据列表
        Example<TestTool> example = Example.of(testTool, matcher);
        Page<TestTool> list = testToolService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/testTool/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:testTool:add")
    public String toAdd() {
        return "/maintenance/testTool/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:testTool:edit")
    public String toEdit(@PathVariable("id") TestTool testTool, Model model) {
        model.addAttribute("testTool", testTool);
        return "/maintenance/testTool/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:testTool:add", "maintenance:testTool:edit"})
    @ResponseBody
    public ResultVo save(@Validated TestToolValid valid, TestTool testTool) {
        // 复制保留无需修改的数据
        if (testTool.getId() != null) {
            TestTool beTestTool = testToolService.getById(testTool.getId());
            EntityBeanUtil.copyProperties(beTestTool, testTool);
        }

        // 保存数据
        testToolService.save(testTool);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:testTool:detail")
    public String toDetail(@PathVariable("id") TestTool testTool, Model model) {
        model.addAttribute("testTool",testTool);
        return "/maintenance/testTool/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:testTool:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (testToolService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}