package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.PatchBad;
import com.linln.admin.produce.service.PatchBadService;
import com.linln.admin.produce.validator.PatchBadValid;
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
@RequestMapping("/produce/patchBad")
public class PatchBadController {

    @Autowired
    private PatchBadService patchBadService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("produce:patchBad:index")
    public String index(Model model, PatchBad patchBad) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_code", match -> match.contains())
                .withMatcher("patch_device", match -> match.contains())
                .withMatcher("bad_type", match -> match.contains())
                .withMatcher("bad_view", match -> match.contains());

        // 获取数据列表
        Example<PatchBad> example = Example.of(patchBad, matcher);
        Page<PatchBad> list = patchBadService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/produce/patchBad/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("produce:patchBad:add")
    public String toAdd() {
        return "/produce/patchBad/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("produce:patchBad:edit")
    public String toEdit(@PathVariable("id") PatchBad patchBad, Model model) {
        model.addAttribute("patchBad", patchBad);
        return "/produce/patchBad/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"produce:patchBad:add", "produce:patchBad:edit"})
    @ResponseBody
    public ResultVo save(@Validated PatchBadValid valid, PatchBad patchBad) {
        // 复制保留无需修改的数据
        if (patchBad.getId() != null) {
            PatchBad bePatchBad = patchBadService.getById(patchBad.getId());
            EntityBeanUtil.copyProperties(bePatchBad, patchBad);
        }

        // 保存数据
        patchBadService.save(patchBad);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("produce:patchBad:detail")
    public String toDetail(@PathVariable("id") PatchBad patchBad, Model model) {
        model.addAttribute("patchBad",patchBad);
        return "/produce/patchBad/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("produce:patchBad:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (patchBadService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}