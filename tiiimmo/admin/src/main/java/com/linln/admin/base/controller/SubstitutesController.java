package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Substitutes;
import com.linln.admin.base.service.SubstitutesService;
import com.linln.admin.base.validator.SubstitutesValid;
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
@RequestMapping("/base/substitutes")
public class SubstitutesController {

    @Autowired
    private SubstitutesService substitutesService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:substitutes:index")
    public String index(Model model, Substitutes substitutes) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Substitutes> example = Example.of(substitutes, matcher);
        Page<Substitutes> list = substitutesService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/substitutes/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:substitutes:add")
    public String toAdd() {
        return "/base/substitutes/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:substitutes:edit")
    public String toEdit(@PathVariable("id") Substitutes substitutes, Model model) {
        model.addAttribute("substitutes", substitutes);
        return "/base/substitutes/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:substitutes:add", "base:substitutes:edit"})
    @ResponseBody
    public ResultVo save(@Validated SubstitutesValid valid, Substitutes substitutes) {
        // 复制保留无需修改的数据
        if (substitutes.getId() != null) {
            Substitutes beSubstitutes = substitutesService.getById(substitutes.getId());
            EntityBeanUtil.copyProperties(beSubstitutes, substitutes);
        }

        // 保存数据
        substitutesService.save(substitutes);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:substitutes:detail")
    public String toDetail(@PathVariable("id") Substitutes substitutes, Model model) {
        model.addAttribute("substitutes",substitutes);
        return "/base/substitutes/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:substitutes:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (substitutesService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}