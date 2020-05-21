package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.PcbCirculation;
import com.linln.admin.produce.service.PcbCirculationService;
import com.linln.admin.produce.validator.PcbCirculationValid;
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
 * @date 2020/05/21
 */
@Controller
@RequestMapping("/produce/pcbCirculation")
public class PcbCirculationController {

    @Autowired
    private PcbCirculationService pcbCirculationService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("produce:pcbCirculation:index")
    public String index(Model model, PcbCirculation pcbCirculation) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_code", match -> match.contains());

        // 获取数据列表
        Example<PcbCirculation> example = Example.of(pcbCirculation, matcher);
        Page<PcbCirculation> list = pcbCirculationService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/produce/pcbCirculation/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("produce:pcbCirculation:add")
    public String toAdd() {
        return "/produce/pcbCirculation/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("produce:pcbCirculation:edit")
    public String toEdit(@PathVariable("id") PcbCirculation pcbCirculation, Model model) {
        model.addAttribute("pcbCirculation", pcbCirculation);
        return "/produce/pcbCirculation/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"produce:pcbCirculation:add", "produce:pcbCirculation:edit"})
    @ResponseBody
    public ResultVo save(@Validated PcbCirculationValid valid, PcbCirculation pcbCirculation) {
        // 复制保留无需修改的数据
        if (pcbCirculation.getId() != null) {
            PcbCirculation bePcbCirculation = pcbCirculationService.getById(pcbCirculation.getId());
            EntityBeanUtil.copyProperties(bePcbCirculation, pcbCirculation);
        }

        // 保存数据
        pcbCirculationService.save(pcbCirculation);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("produce:pcbCirculation:detail")
    public String toDetail(@PathVariable("id") PcbCirculation pcbCirculation, Model model) {
        model.addAttribute("pcbCirculation",pcbCirculation);
        return "/produce/pcbCirculation/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("produce:pcbCirculation:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (pcbCirculationService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}