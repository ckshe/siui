package com.linln.admin.steelMesh.controller;

import com.linln.admin.steelMesh.domain.SteelMesh;
import com.linln.admin.steelMesh.service.SteelMeshService;
import com.linln.admin.steelMesh.validator.SteelMeshValid;
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
@RequestMapping("/steelMesh/steelMesh")
public class SteelMeshController {

    @Autowired
    private SteelMeshService steelMeshService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("steelMesh:steelMesh:index")
    public String index(Model model, SteelMesh steelMesh) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("plateNo", match -> match.contains())
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<SteelMesh> example = Example.of(steelMesh, matcher);
        Page<SteelMesh> list = steelMeshService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/steelMesh/steelMesh/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("steelMesh:steelMesh:add")
    public String toAdd() {
        return "/steelMesh/steelMesh/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("steelMesh:steelMesh:edit")
    public String toEdit(@PathVariable("id") SteelMesh steelMesh, Model model) {
        model.addAttribute("steelMesh", steelMesh);
        return "/steelMesh/steelMesh/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"steelMesh:steelMesh:add", "steelMesh:steelMesh:edit"})
    @ResponseBody
    public ResultVo save(@Validated SteelMeshValid valid, SteelMesh steelMesh) {
        // 复制保留无需修改的数据
        if (steelMesh.getId() != null) {
            SteelMesh beSteelMesh = steelMeshService.getById(steelMesh.getId());
            EntityBeanUtil.copyProperties(beSteelMesh, steelMesh);
        }

        // 保存数据
        steelMeshService.save(steelMesh);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("steelMesh:steelMesh:detail")
    public String toDetail(@PathVariable("id") SteelMesh steelMesh, Model model) {
        model.addAttribute("steelMesh",steelMesh);
        return "/steelMesh/steelMesh/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("steelMesh:steelMesh:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (steelMeshService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}