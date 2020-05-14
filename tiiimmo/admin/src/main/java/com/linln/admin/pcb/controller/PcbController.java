package com.linln.admin.pcb.controller;

import com.linln.admin.pcb.domain.Pcb;
import com.linln.admin.pcb.service.PcbService;
import com.linln.admin.pcb.validator.PcbValid;
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
@RequestMapping("/pcb/pcb")
public class PcbController {

    @Autowired
    private PcbService pcbService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("pcb:pcb:index")
    public String index(Model model, Pcb pcb) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("version", match -> match.contains())
                .withMatcher("name", match -> match.contains())
                .withMatcher("light_plate", match -> match.contains());

        // 获取数据列表
        Example<Pcb> example = Example.of(pcb, matcher);
        Page<Pcb> list = pcbService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/pcb/pcb/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("pcb:pcb:add")
    public String toAdd() {
        return "/pcb/pcb/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("pcb:pcb:edit")
    public String toEdit(@PathVariable("id") Pcb pcb, Model model) {
        model.addAttribute("pcb", pcb);
        return "/pcb/pcb/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"pcb:pcb:add", "pcb:pcb:edit"})
    @ResponseBody
    public ResultVo save(@Validated PcbValid valid, Pcb pcb) {
        // 复制保留无需修改的数据
        if (pcb.getId() != null) {
            Pcb bePcb = pcbService.getById(pcb.getId());
            EntityBeanUtil.copyProperties(bePcb, pcb);
        }

        // 保存数据
        pcbService.save(pcb);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("pcb:pcb:detail")
    public String toDetail(@PathVariable("id") Pcb pcb, Model model) {
        model.addAttribute("pcb",pcb);
        return "/pcb/pcb/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("pcb:pcb:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (pcbService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}