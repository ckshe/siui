package com.linln.admin.base.controller;

import com.linln.admin.base.domain.ProductionShift;
import com.linln.admin.base.service.ProductionShiftService;
import com.linln.admin.base.validator.ProductionShiftValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.UserService;
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
@RequestMapping("/base/productionShift")
public class ProductionShiftController {

    @Autowired
    private ProductionShiftService productionShiftService;

    @Autowired
    private UserService userService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:productionShift:index")
    public String index(Model model, ProductionShift productionShift) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("userName", match -> match.contains())
                .withMatcher("station", match -> match.contains());

        // 获取数据列表
        Example<ProductionShift> example = Example.of(productionShift, matcher);
        Page<ProductionShift> list = productionShiftService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/productionShift/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:productionShift:add")
    public String toAdd(Model model) {
        model.addAttribute("users",userService.queryUsers());
        return "/base/productionShift/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:productionShift:edit")
    public String toEdit(@PathVariable("id") ProductionShift productionShift, Model model) {
        model.addAttribute("users",userService.queryUsers());
        model.addAttribute("productionShift", productionShift);
        return "/base/productionShift/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:productionShift:add", "base:productionShift:edit"})
    @ResponseBody
    public ResultVo save(@Validated ProductionShiftValid valid, ProductionShift productionShift) {
        // 复制保留无需修改的数据
        if (productionShift.getId() != null) {
            ProductionShift beProductionShift = productionShiftService.getById(productionShift.getId());
            EntityBeanUtil.copyProperties(beProductionShift, productionShift);
        }

        // 保存数据
        productionShiftService.save(productionShift);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:productionShift:detail")
    public String toDetail(@PathVariable("id") ProductionShift productionShift, Model model) {
        model.addAttribute("productionShift",productionShift);
        return "/base/productionShift/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:productionShift:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (productionShiftService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}