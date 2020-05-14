package com.linln.admin.base.controller;

import com.linln.admin.base.domain.LossStock;
import com.linln.admin.base.service.LossStockService;
import com.linln.admin.base.validator.LossStockValid;
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
 * @author www
 * @date 2020/05/13
 */
@Controller
@RequestMapping("/base/lossStock")
public class LossStockController {

    @Autowired
    private LossStockService lossStockService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("lossStock:lossStock:index")
    public String index(Model model, LossStock lossStock) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("product_code", match -> match.contains())
                .withMatcher("product_name", match -> match.contains());

        // 获取数据列表
        Example<LossStock> example = Example.of(lossStock, matcher);
        Page<LossStock> list = lossStockService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/lossStock/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("lossStock:lossStock:add")
    public String toAdd() {
        return "/base/lossStock/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("lossStock:lossStock:edit")
    public String toEdit(@PathVariable("id") LossStock lossStock, Model model) {
        model.addAttribute("lossStock", lossStock);
        return "/base/lossStock/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"lossStock:lossStock:add", "lossStock:lossStock:edit"})
    @ResponseBody
    public ResultVo save(@Validated LossStockValid valid, LossStock lossStock) {
        // 复制保留无需修改的数据
        if (lossStock.getId() != null) {
            LossStock beLossStock = lossStockService.getById(lossStock.getId());
            EntityBeanUtil.copyProperties(beLossStock, lossStock);
        }

        // 保存数据
        lossStockService.save(lossStock);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("lossStock:lossStock:detail")
    public String toDetail(@PathVariable("id") LossStock lossStock, Model model) {
        model.addAttribute("lossStock",lossStock);
        return "/base/lossStock/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("lossStock:lossStock:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (lossStockService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}