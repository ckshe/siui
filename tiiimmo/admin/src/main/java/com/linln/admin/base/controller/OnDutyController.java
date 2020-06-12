package com.linln.admin.base.controller;

import com.linln.admin.base.domain.OnDuty;
import com.linln.admin.base.service.OnDutyService;
import com.linln.admin.base.validator.OnDutyValid;
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
 * @date 2020/06/12
 */
@Controller
@RequestMapping("/base/onDuty")
public class OnDutyController {

    @Autowired
    private OnDutyService onDutyService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:onDuty:index")
    public String index(Model model, OnDuty onDuty) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("userName", match -> match.contains())
                .withMatcher("station", match -> match.contains());

        // 获取数据列表
        Example<OnDuty> example = Example.of(onDuty, matcher);
        Page<OnDuty> list = onDutyService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/onDuty/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:onDuty:add")
    public String toAdd() {
        return "/base/onDuty/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:onDuty:edit")
    public String toEdit(@PathVariable("id") OnDuty onDuty, Model model) {
        model.addAttribute("onDuty", onDuty);
        return "/base/onDuty/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:onDuty:add", "base:onDuty:edit"})
    @ResponseBody
    public ResultVo save(@Validated OnDutyValid valid, OnDuty onDuty) {
        // 复制保留无需修改的数据
        if (onDuty.getId() != null) {
            OnDuty beOnDuty = onDutyService.getById(onDuty.getId());
            EntityBeanUtil.copyProperties(beOnDuty, onDuty);
        }

        // 保存数据
        onDutyService.save(onDuty);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:onDuty:detail")
    public String toDetail(@PathVariable("id") OnDuty onDuty, Model model) {
        model.addAttribute("onDuty",onDuty);
        return "/base/onDuty/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:onDuty:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (onDutyService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}