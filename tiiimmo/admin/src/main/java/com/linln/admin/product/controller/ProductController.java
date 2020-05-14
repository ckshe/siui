package com.linln.admin.product.controller;

import com.linln.admin.product.domain.Product;
import com.linln.admin.product.service.ProductService;
import com.linln.admin.product.validator.ProductValid;
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
@RequestMapping("/product/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("product:product:index")
    public String index(Model model, Product product) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Product> example = Example.of(product, matcher);
        Page<Product> list = productService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/product/product/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("product:product:add")
    public String toAdd() {
        return "/product/product/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("product:product:edit")
    public String toEdit(@PathVariable("id") Product product, Model model) {
        model.addAttribute("product", product);
        return "/product/product/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"product:product:add", "product:product:edit"})
    @ResponseBody
    public ResultVo save(@Validated ProductValid valid, Product product) {
        // 复制保留无需修改的数据
        if (product.getId() != null) {
            Product beProduct = productService.getById(product.getId());
            EntityBeanUtil.copyProperties(beProduct, product);
        }

        // 保存数据
        productService.save(product);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("product:product:detail")
    public String toDetail(@PathVariable("id") Product product, Model model) {
        model.addAttribute("product",product);
        return "/product/product/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("product:product:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (productService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}