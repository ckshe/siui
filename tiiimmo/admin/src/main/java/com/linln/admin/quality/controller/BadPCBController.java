package com.linln.admin.quality.controller;

import com.linln.admin.quality.domain.BadPCB;
import com.linln.admin.quality.service.BadPCBService;
import com.linln.admin.quality.validator.BadPCBValid;
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
@RequestMapping("/quality/badPCB")
public class BadPCBController {

    @Autowired
    private BadPCBService badPCBService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("quality:badPCB:index")
    public String index(Model model, BadPCB badPCB) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("manufactureCode", match -> match.contains());

        // 获取数据列表
        Example<BadPCB> example = Example.of(badPCB, matcher);
        Page<BadPCB> list = badPCBService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/quality/badPCB/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("quality:badPCB:add")
    public String toAdd() {
        return "/quality/badPCB/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("quality:badPCB:edit")
    public String toEdit(@PathVariable("id") BadPCB badPCB, Model model) {
        model.addAttribute("badPCB", badPCB);
        return "/quality/badPCB/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"quality:badPCB:add", "quality:badPCB:edit"})
    @ResponseBody
    public ResultVo save(@Validated BadPCBValid valid, BadPCB badPCB) {
        // 复制保留无需修改的数据
        if (badPCB.getId() != null) {
            BadPCB beBadPCB = badPCBService.getById(badPCB.getId());
            EntityBeanUtil.copyProperties(beBadPCB, badPCB);
        }

        // 保存数据
        badPCBService.save(badPCB);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("quality:badPCB:detail")
    public String toDetail(@PathVariable("id") BadPCB badPCB, Model model) {
        model.addAttribute("badPCB",badPCB);
        return "/quality/badPCB/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("quality:badPCB:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (badPCBService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}