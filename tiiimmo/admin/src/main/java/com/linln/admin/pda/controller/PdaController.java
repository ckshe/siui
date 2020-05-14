package com.linln.admin.pda.controller;

import com.linln.admin.pda.domain.Pda;
import com.linln.admin.pda.service.PdaService;
import com.linln.admin.pda.validator.PdaValid;
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
@RequestMapping("/pda/pda")
public class PdaController {

    @Autowired
    private PdaService pdaService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("pda:pda:index")
    public String index(Model model, Pda pda) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Pda> example = Example.of(pda, matcher);
        Page<Pda> list = pdaService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/pda/pda/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("pda:pda:add")
    public String toAdd() {
        return "/pda/pda/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("pda:pda:edit")
    public String toEdit(@PathVariable("id") Pda pda, Model model) {
        model.addAttribute("pda", pda);
        return "/pda/pda/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"pda:pda:add", "pda:pda:edit"})
    @ResponseBody
    public ResultVo save(@Validated PdaValid valid, Pda pda) {
        // 复制保留无需修改的数据
        if (pda.getId() != null) {
            Pda bePda = pdaService.getById(pda.getId());
            EntityBeanUtil.copyProperties(bePda, pda);
        }

        // 保存数据
        pdaService.save(pda);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("pda:pda:detail")
    public String toDetail(@PathVariable("id") Pda pda, Model model) {
        model.addAttribute("pda",pda);
        return "/pda/pda/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("pda:pda:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (pdaService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}