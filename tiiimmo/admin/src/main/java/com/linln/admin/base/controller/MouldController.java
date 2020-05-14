package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Mould;
import com.linln.admin.base.service.MouldService;
import com.linln.admin.base.validator.MouldValid;
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
 * @date 2020/05/14
 */
@Controller
@RequestMapping("/base/mould")
public class MouldController {

    @Autowired
    private MouldService mouldService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("mould:mould:index")
    public String index(Model model, Mould mould) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains());

        // 获取数据列表
        Example<Mould> example = Example.of(mould, matcher);
        Page<Mould> list = mouldService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/mould/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("mould:mould:add")
    public String toAdd() {
        return "/base/mould/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("mould:mould:edit")
    public String toEdit(@PathVariable("id") Mould mould, Model model) {
        model.addAttribute("mould", mould);
        return "/base/mould/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"mould:mould:add", "mould:mould:edit"})
    @ResponseBody
    public ResultVo save(@Validated MouldValid valid, Mould mould) {
        // 复制保留无需修改的数据
        if (mould.getId() != null) {
            Mould beMould = mouldService.getById(mould.getId());
            EntityBeanUtil.copyProperties(beMould, mould);
        }

        // 保存数据
        mouldService.save(mould);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("mould:mould:detail")
    public String toDetail(@PathVariable("id") Mould mould, Model model) {
        model.addAttribute("mould",mould);
        return "/base/mould/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("mould:mould:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (mouldService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}