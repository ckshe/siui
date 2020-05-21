package com.linln.admin.equipmentMonitoring.controller;

import com.linln.admin.equipmentMonitoring.domain.ProductionStatus;
import com.linln.admin.equipmentMonitoring.service.ProductionStatusService;
import com.linln.admin.equipmentMonitoring.validator.ProductionStatusValid;
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
@RequestMapping("/equipmentMonitoring/productionStatus")
public class ProductionStatusController {

    @Autowired
    private ProductionStatusService productionStatusService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("equipmentMonitoring:productionStatus:index")
    public String index(Model model, ProductionStatus productionStatus) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("deviceCode", match -> match.contains());

        // 获取数据列表
        Example<ProductionStatus> example = Example.of(productionStatus, matcher);
        Page<ProductionStatus> list = productionStatusService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/equipmentMonitoring/productionStatus/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("equipmentMonitoring:productionStatus:add")
    public String toAdd() {
        return "/equipmentMonitoring/productionStatus/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("equipmentMonitoring:productionStatus:edit")
    public String toEdit(@PathVariable("id") ProductionStatus productionStatus, Model model) {
        model.addAttribute("productionStatus", productionStatus);
        return "/equipmentMonitoring/productionStatus/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"equipmentMonitoring:productionStatus:add", "equipmentMonitoring:productionStatus:edit"})
    @ResponseBody
    public ResultVo save(@Validated ProductionStatusValid valid, ProductionStatus productionStatus) {
        // 复制保留无需修改的数据
        if (productionStatus.getId() != null) {
            ProductionStatus beProductionStatus = productionStatusService.getById(productionStatus.getId());
            EntityBeanUtil.copyProperties(beProductionStatus, productionStatus);
        }

        // 保存数据
        productionStatusService.save(productionStatus);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("equipmentMonitoring:productionStatus:detail")
    public String toDetail(@PathVariable("id") ProductionStatus productionStatus, Model model) {
        model.addAttribute("productionStatus",productionStatus);
        return "/equipmentMonitoring/productionStatus/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("equipmentMonitoring:productionStatus:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (productionStatusService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}