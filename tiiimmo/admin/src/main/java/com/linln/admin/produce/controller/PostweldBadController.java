package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.PostweldBad;
import com.linln.admin.produce.service.PostweldBadService;
import com.linln.admin.produce.validator.PostweldBadValid;
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
@RequestMapping("/produce/postweldBad")
public class PostweldBadController {

    @Autowired
    private PostweldBadService postweldBadService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("produce:postweldBad:index")
    public String index(Model model, PostweldBad postweldBad) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_code", match -> match.contains())
                .withMatcher("pacth_device", match -> match.contains());

        // 获取数据列表
        Example<PostweldBad> example = Example.of(postweldBad, matcher);
        Page<PostweldBad> list = postweldBadService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/produce/postweldBad/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("produce:postweldBad:add")
    public String toAdd() {
        return "/produce/postweldBad/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("produce:postweldBad:edit")
    public String toEdit(@PathVariable("id") PostweldBad postweldBad, Model model) {
        model.addAttribute("postweldBad", postweldBad);
        return "/produce/postweldBad/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"produce:postweldBad:add", "produce:postweldBad:edit"})
    @ResponseBody
    public ResultVo save(@Validated PostweldBadValid valid, PostweldBad postweldBad) {
        // 复制保留无需修改的数据
        if (postweldBad.getId() != null) {
            PostweldBad bePostweldBad = postweldBadService.getById(postweldBad.getId());
            EntityBeanUtil.copyProperties(bePostweldBad, postweldBad);
        }

        // 保存数据
        postweldBadService.save(postweldBad);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("produce:postweldBad:detail")
    public String toDetail(@PathVariable("id") PostweldBad postweldBad, Model model) {
        model.addAttribute("postweldBad",postweldBad);
        return "/produce/postweldBad/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("produce:postweldBad:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (postweldBadService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}