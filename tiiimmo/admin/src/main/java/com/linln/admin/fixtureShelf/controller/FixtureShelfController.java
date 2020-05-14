package com.linln.admin.fixtureShelf.controller;

import com.linln.admin.fixtureShelf.domain.FixtureShelf;
import com.linln.admin.fixtureShelf.service.FixtureShelfService;
import com.linln.admin.fixtureShelf.validator.FixtureShelfValid;
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
@RequestMapping("/fixtureShelf/fixtureShelf")
public class FixtureShelfController {

    @Autowired
    private FixtureShelfService fixtureShelfService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("fixtureShelf:fixtureShelf:index")
    public String index(Model model, FixtureShelf fixtureShelf) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("shelfNo", match -> match.contains());

        // 获取数据列表
        Example<FixtureShelf> example = Example.of(fixtureShelf, matcher);
        Page<FixtureShelf> list = fixtureShelfService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/fixtureShelf/fixtureShelf/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("fixtureShelf:fixtureShelf:add")
    public String toAdd() {
        return "/fixtureShelf/fixtureShelf/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("fixtureShelf:fixtureShelf:edit")
    public String toEdit(@PathVariable("id") FixtureShelf fixtureShelf, Model model) {
        model.addAttribute("fixtureShelf", fixtureShelf);
        return "/fixtureShelf/fixtureShelf/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"fixtureShelf:fixtureShelf:add", "fixtureShelf:fixtureShelf:edit"})
    @ResponseBody
    public ResultVo save(@Validated FixtureShelfValid valid, FixtureShelf fixtureShelf) {
        // 复制保留无需修改的数据
        if (fixtureShelf.getId() != null) {
            FixtureShelf beFixtureShelf = fixtureShelfService.getById(fixtureShelf.getId());
            EntityBeanUtil.copyProperties(beFixtureShelf, fixtureShelf);
        }

        // 保存数据
        fixtureShelfService.save(fixtureShelf);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("fixtureShelf:fixtureShelf:detail")
    public String toDetail(@PathVariable("id") FixtureShelf fixtureShelf, Model model) {
        model.addAttribute("fixtureShelf",fixtureShelf);
        return "/fixtureShelf/fixtureShelf/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("fixtureShelf:fixtureShelf:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (fixtureShelfService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}