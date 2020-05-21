package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.TestContent;
import com.linln.admin.maintenance.service.TestContentService;
import com.linln.admin.maintenance.validator.TestContentValid;
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
@RequestMapping("/maintenance/testContent")
public class TestContentController {

    @Autowired
    private TestContentService testContentService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:testContent:index")
    public String index(Model model, TestContent testContent) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("deviceType", match -> match.contains());

        // 获取数据列表
        Example<TestContent> example = Example.of(testContent, matcher);
        Page<TestContent> list = testContentService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/testContent/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:testContent:add")
    public String toAdd() {
        return "/maintenance/testContent/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:testContent:edit")
    public String toEdit(@PathVariable("id") TestContent testContent, Model model) {
        model.addAttribute("testContent", testContent);
        return "/maintenance/testContent/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:testContent:add", "maintenance:testContent:edit"})
    @ResponseBody
    public ResultVo save(@Validated TestContentValid valid, TestContent testContent) {
        // 复制保留无需修改的数据
        if (testContent.getId() != null) {
            TestContent beTestContent = testContentService.getById(testContent.getId());
            EntityBeanUtil.copyProperties(beTestContent, testContent);
        }

        // 保存数据
        testContentService.save(testContent);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:testContent:detail")
    public String toDetail(@PathVariable("id") TestContent testContent, Model model) {
        model.addAttribute("testContent",testContent);
        return "/maintenance/testContent/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:testContent:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (testContentService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}