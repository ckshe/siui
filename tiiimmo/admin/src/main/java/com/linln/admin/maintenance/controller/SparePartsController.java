package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.SpareParts;
import com.linln.admin.maintenance.service.SparePartsService;
import com.linln.admin.maintenance.validator.SparePartsValid;
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
@RequestMapping("/maintenance/spareParts")
public class SparePartsController {

    @Autowired
    private SparePartsService sparePartsService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:spareParts:index")
    public String index(Model model, SpareParts spareParts) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains());

        // 获取数据列表
        Example<SpareParts> example = Example.of(spareParts, matcher);
        Page<SpareParts> list = sparePartsService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/spareParts/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:spareParts:add")
    public String toAdd() {
        return "/maintenance/spareParts/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:spareParts:edit")
    public String toEdit(@PathVariable("id") SpareParts spareParts, Model model) {
        model.addAttribute("spareParts", spareParts);
        return "/maintenance/spareParts/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:spareParts:add", "maintenance:spareParts:edit"})
    @ResponseBody
    public ResultVo save(@Validated SparePartsValid valid, SpareParts spareParts) {
        // 复制保留无需修改的数据
        if (spareParts.getId() != null) {
            SpareParts beSpareParts = sparePartsService.getById(spareParts.getId());
            EntityBeanUtil.copyProperties(beSpareParts, spareParts);
        }

        // 保存数据
        sparePartsService.save(spareParts);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:spareParts:detail")
    public String toDetail(@PathVariable("id") SpareParts spareParts, Model model) {
        model.addAttribute("spareParts",spareParts);
        return "/maintenance/spareParts/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:spareParts:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (sparePartsService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}