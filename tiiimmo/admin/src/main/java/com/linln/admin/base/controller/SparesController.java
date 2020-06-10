package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Spares;
import com.linln.admin.base.service.SparesService;
import com.linln.admin.base.validator.SparesValid;
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
 * @date 2020/06/10
 */
@Controller
@RequestMapping("/base/spares")
public class SparesController {

    @Autowired
    private SparesService sparesService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:spares:index")
    public String index(Model model, Spares spares) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Spares> example = Example.of(spares, matcher);
        Page<Spares> list = sparesService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/spares/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:spares:add")
    public String toAdd() {
        return "/base/spares/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:spares:edit")
    public String toEdit(@PathVariable("id") Spares spares, Model model) {
        model.addAttribute("spares", spares);
        return "/base/spares/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:spares:add", "base:spares:edit"})
    @ResponseBody
    public ResultVo save(@Validated SparesValid valid, Spares spares) {
        // 复制保留无需修改的数据
        if (spares.getId() != null) {
            Spares beSpares = sparesService.getById(spares.getId());
            EntityBeanUtil.copyProperties(beSpares, spares);
        }

        // 保存数据
        sparesService.save(spares);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:spares:detail")
    public String toDetail(@PathVariable("id") Spares spares, Model model) {
        model.addAttribute("spares",spares);
        return "/base/spares/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:spares:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (sparesService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}