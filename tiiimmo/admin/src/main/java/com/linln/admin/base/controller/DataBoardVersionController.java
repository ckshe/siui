package com.linln.admin.base.controller;

import com.linln.admin.base.domain.DataBoardVersion;
import com.linln.admin.base.service.DataBoardVersionService;
import com.linln.admin.base.validator.DataBoardVersionValid;
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
 * @date 2020/07/07
 */
@Controller
@RequestMapping("/base/dataBoardVersion")
public class DataBoardVersionController {

    @Autowired
    private DataBoardVersionService dataBoardVersionService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:dataBoardVersion:index")
    public String index(Model model, DataBoardVersion dataBoardVersion) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("versionNo", match -> match.contains());

        // 获取数据列表
        Example<DataBoardVersion> example = Example.of(dataBoardVersion, matcher);
        Page<DataBoardVersion> list = dataBoardVersionService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/dataBoardVersion/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:dataBoardVersion:add")
    public String toAdd() {
        return "/base/dataBoardVersion/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:dataBoardVersion:edit")
    public String toEdit(@PathVariable("id") DataBoardVersion dataBoardVersion, Model model) {
        model.addAttribute("dataBoardVersion", dataBoardVersion);
        return "/base/dataBoardVersion/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:dataBoardVersion:add", "base:dataBoardVersion:edit"})
    @ResponseBody
    public ResultVo save(@Validated DataBoardVersionValid valid, DataBoardVersion dataBoardVersion) {
        // 复制保留无需修改的数据
        if (dataBoardVersion.getId() != null) {
            DataBoardVersion beDataBoardVersion = dataBoardVersionService.getById(dataBoardVersion.getId());
            EntityBeanUtil.copyProperties(beDataBoardVersion, dataBoardVersion);
        }

        // 保存数据
        dataBoardVersionService.save(dataBoardVersion);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:dataBoardVersion:detail")
    public String toDetail(@PathVariable("id") DataBoardVersion dataBoardVersion, Model model) {
        model.addAttribute("dataBoardVersion",dataBoardVersion);
        return "/base/dataBoardVersion/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:dataBoardVersion:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (dataBoardVersionService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}