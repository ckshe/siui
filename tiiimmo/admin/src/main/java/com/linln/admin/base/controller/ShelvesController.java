package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Shelves;
import com.linln.admin.base.service.ShelvesService;
import com.linln.admin.base.validator.ShelvesValid;
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
@RequestMapping("/base/shelves")
public class ShelvesController {

    @Autowired
    private ShelvesService shelvesService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("shelves:shelves:index")
    public String index(Model model, Shelves shelves) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Shelves> example = Example.of(shelves, matcher);
        Page<Shelves> list = shelvesService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/shelves/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("shelves:shelves:add")
    public String toAdd() {
        return "/base/shelves/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("shelves:shelves:edit")
    public String toEdit(@PathVariable("id") Shelves shelves, Model model) {
        model.addAttribute("shelves", shelves);
        return "/base/shelves/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"shelves:shelves:add", "shelves:shelves:edit"})
    @ResponseBody
    public ResultVo save(@Validated ShelvesValid valid, Shelves shelves) {
        // 复制保留无需修改的数据
        if (shelves.getId() != null) {
            Shelves beShelves = shelvesService.getById(shelves.getId());
            EntityBeanUtil.copyProperties(beShelves, shelves);
        }

        // 保存数据
        shelvesService.save(shelves);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("shelves:shelves:detail")
    public String toDetail(@PathVariable("id") Shelves shelves, Model model) {
        model.addAttribute("shelves",shelves);
        return "/base/shelves/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("shelves:shelves:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (shelvesService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}