package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.KeepRecord;
import com.linln.admin.maintenance.service.KeepRecordService;
import com.linln.admin.maintenance.validator.KeepRecordValid;
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
 * @author lian
 * @date 2020/08/19
 */
@Controller
@RequestMapping("/maintenance/keepRecord")
public class KeepRecordController {

    @Autowired
    private KeepRecordService keepRecordService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:keepRecord:index")
    public String index(Model model, KeepRecord keepRecord) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("keepDevice", match -> match.contains())
                .withMatcher("keepUser", match -> match.contains());

        // 获取数据列表
        Example<KeepRecord> example = Example.of(keepRecord, matcher);
        Page<KeepRecord> list = keepRecordService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/keepRecord/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:keepRecord:add")
    public String toAdd() {
        return "/maintenance/keepRecord/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:keepRecord:edit")
    public String toEdit(@PathVariable("id") KeepRecord keepRecord, Model model) {
        model.addAttribute("keepRecord", keepRecord);
        return "/maintenance/keepRecord/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:keepRecord:add", "maintenance:keepRecord:edit"})
    @ResponseBody
    public ResultVo save(@Validated KeepRecordValid valid, KeepRecord keepRecord) {
        // 复制保留无需修改的数据
        if (keepRecord.getId() != null) {
            KeepRecord beKeepRecord = keepRecordService.getById(keepRecord.getId());
            EntityBeanUtil.copyProperties(beKeepRecord, keepRecord);
        }

        // 保存数据
        keepRecordService.save(keepRecord);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:keepRecord:detail")
    public String toDetail(@PathVariable("id") KeepRecord keepRecord, Model model) {
        model.addAttribute("keepRecord",keepRecord);
        return "/maintenance/keepRecord/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:keepRecord:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (keepRecordService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}