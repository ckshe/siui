package com.linln.admin.solder.controller;

import com.linln.admin.solder.domain.Solder;
import com.linln.admin.solder.service.SolderService;
import com.linln.admin.solder.validator.SolderValid;
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
@RequestMapping("/solder/solder")
public class SolderController {

    @Autowired
    private SolderService solderService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("solder:solder:index")
    public String index(Model model, Solder solder) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Solder> example = Example.of(solder, matcher);
        Page<Solder> list = solderService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/solder/solder/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("solder:solder:add")
    public String toAdd() {
        return "/solder/solder/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("solder:solder:edit")
    public String toEdit(@PathVariable("id") Solder solder, Model model) {
        model.addAttribute("solder", solder);
        return "/solder/solder/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"solder:solder:add", "solder:solder:edit"})
    @ResponseBody
    public ResultVo save(@Validated SolderValid valid, Solder solder) {
        // 复制保留无需修改的数据
        if (solder.getId() != null) {
            Solder beSolder = solderService.getById(solder.getId());
            EntityBeanUtil.copyProperties(beSolder, solder);
        }

        // 保存数据
        solderService.save(solder);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("solder:solder:detail")
    public String toDetail(@PathVariable("id") Solder solder, Model model) {
        model.addAttribute("solder",solder);
        return "/solder/solder/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("solder:solder:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (solderService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}