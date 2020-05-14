package com.linln.admin.base.controller;

import com.linln.admin.base.domain.EditFixture;
import com.linln.admin.base.service.EditFixtureService;
import com.linln.admin.base.validator.EditFixtureValid;
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
@RequestMapping("/base/editFixture")
public class EditFixtureController {

    @Autowired
    private EditFixtureService editFixtureService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("fixture:editFixture:index")
    public String index(Model model, EditFixture editFixture) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("plateNo", match -> match.contains());

        // 获取数据列表
        Example<EditFixture> example = Example.of(editFixture, matcher);
        Page<EditFixture> list = editFixtureService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/editFixture/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("fixture:editFixture:add")
    public String toAdd() {
        return "/base/editFixture/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("fixture:editFixture:edit")
    public String toEdit(@PathVariable("id") EditFixture editFixture, Model model) {
        model.addAttribute("editFixture", editFixture);
        return "/base/editFixture/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"fixture:editFixture:add", "fixture:editFixture:edit"})
    @ResponseBody
    public ResultVo save(@Validated EditFixtureValid valid, EditFixture editFixture) {
        // 复制保留无需修改的数据
        if (editFixture.getId() != null) {
            EditFixture beEditFixture = editFixtureService.getById(editFixture.getId());
            EntityBeanUtil.copyProperties(beEditFixture, editFixture);
        }

        // 保存数据
        editFixtureService.save(editFixture);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("fixture:editFixture:detail")
    public String toDetail(@PathVariable("id") EditFixture editFixture, Model model) {
        model.addAttribute("editFixture",editFixture);
        return "/base/editFixture/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("fixture:editFixture:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (editFixtureService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}