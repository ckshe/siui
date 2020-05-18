package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Models;
import com.linln.admin.base.service.ModelsService;
import com.linln.admin.base.validator.ModelsValid;
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
 * @date 2020/05/18
 */
@Controller
@RequestMapping("/base/models")
public class ModelsController {

    @Autowired
    private ModelsService modelsService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:models:index")
    public String index(Model model, Models models) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("model_id", match -> match.contains())
                .withMatcher("model_ver", match -> match.contains());

        // 获取数据列表
        Example<Models> example = Example.of(models, matcher);
        Page<Models> list = modelsService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/models/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:models:add")
    public String toAdd() {
        return "/base/models/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:models:edit")
    public String toEdit(@PathVariable("id") Models models, Model model) {
        model.addAttribute("models", models);
        return "/base/models/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:models:add", "base:models:edit"})
    @ResponseBody
    public ResultVo save(@Validated ModelsValid valid, Models models) {
        // 复制保留无需修改的数据
        if (models.getId() != null) {
            Models beModels = modelsService.getById(models.getId());
            EntityBeanUtil.copyProperties(beModels, models);
        }

        // 保存数据
        modelsService.save(models);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:models:detail")
    public String toDetail(@PathVariable("id") Models models, Model model) {
        model.addAttribute("models",models);
        return "/base/models/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:models:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (modelsService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}