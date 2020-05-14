package com.linln.admin.base.controller;

import com.linln.admin.base.domain.MouldPcbDetail;
import com.linln.admin.base.service.MouldPcbDetailService;
import com.linln.admin.base.validator.MouldPcbDetailValid;
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
@RequestMapping("/base/mouldPcbDetail")
public class MouldPcbDetailController {

    @Autowired
    private MouldPcbDetailService mouldPcbDetailService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("mould:mouldPcbDetail:index")
    public String index(Model model, MouldPcbDetail mouldPcbDetail) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_code", match -> match.contains());

        // 获取数据列表
        Example<MouldPcbDetail> example = Example.of(mouldPcbDetail, matcher);
        Page<MouldPcbDetail> list = mouldPcbDetailService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/mouldPcbDetail/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("mould:mouldPcbDetail:add")
    public String toAdd() {
        return "/base/mouldPcbDetail/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("mould:mouldPcbDetail:edit")
    public String toEdit(@PathVariable("id") MouldPcbDetail mouldPcbDetail, Model model) {
        model.addAttribute("mouldPcbDetail", mouldPcbDetail);
        return "/base/mouldPcbDetail/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"mould:mouldPcbDetail:add", "mould:mouldPcbDetail:edit"})
    @ResponseBody
    public ResultVo save(@Validated MouldPcbDetailValid valid, MouldPcbDetail mouldPcbDetail) {
        // 复制保留无需修改的数据
        if (mouldPcbDetail.getId() != null) {
            MouldPcbDetail beMouldPcbDetail = mouldPcbDetailService.getById(mouldPcbDetail.getId());
            EntityBeanUtil.copyProperties(beMouldPcbDetail, mouldPcbDetail);
        }

        // 保存数据
        mouldPcbDetailService.save(mouldPcbDetail);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("mould:mouldPcbDetail:detail")
    public String toDetail(@PathVariable("id") MouldPcbDetail mouldPcbDetail, Model model) {
        model.addAttribute("mouldPcbDetail",mouldPcbDetail);
        return "/base/mouldPcbDetail/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("mould:mouldPcbDetail:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (mouldPcbDetailService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}