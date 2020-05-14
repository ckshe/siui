package com.linln.admin.fixture.controller;

import com.linln.admin.fixture.domain.Fixture;
import com.linln.admin.fixture.service.FixtureService;
import com.linln.admin.fixture.validator.FixtureValid;
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
 * @date 2020/05/14
 */
@Controller
@RequestMapping("/fixture/fixture")
public class FixtureController {

    @Autowired
    private FixtureService fixtureService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("fixture:fixture:index")
    public String index(Model model, Fixture fixture) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("num", match -> match.contains());

        // 获取数据列表
        Example<Fixture> example = Example.of(fixture, matcher);
        Page<Fixture> list = fixtureService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/fixture/fixture/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("fixture:fixture:add")
    public String toAdd() {
        return "/fixture/fixture/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("fixture:fixture:edit")
    public String toEdit(@PathVariable("id") Fixture fixture, Model model) {
        model.addAttribute("fixture", fixture);
        return "/fixture/fixture/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"fixture:fixture:add", "fixture:fixture:edit"})
    @ResponseBody
    public ResultVo save(@Validated FixtureValid valid, Fixture fixture) {
        // 复制保留无需修改的数据
        if (fixture.getId() != null) {
            Fixture beFixture = fixtureService.getById(fixture.getId());
            EntityBeanUtil.copyProperties(beFixture, fixture);
        }

        // 保存数据
        fixtureService.save(fixture);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("fixture:fixture:detail")
    public String toDetail(@PathVariable("id") Fixture fixture, Model model) {
        model.addAttribute("fixture",fixture);
        return "/fixture/fixture/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("fixture:fixture:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (fixtureService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}