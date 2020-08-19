package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.CheckRecord;
import com.linln.admin.maintenance.service.CheckRecordService;
import com.linln.admin.maintenance.validator.CheckRecordValid;
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
@RequestMapping("/maintenance/checkRecord")
public class CheckRecordController {

    @Autowired
    private CheckRecordService checkRecordService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:checkRecord:index")
    public String index(Model model, CheckRecord checkRecord) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("checkDevice", match -> match.contains())
                .withMatcher("checkUser", match -> match.contains());

        // 获取数据列表
        Example<CheckRecord> example = Example.of(checkRecord, matcher);
        Page<CheckRecord> list = checkRecordService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/checkRecord/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:checkRecord:add")
    public String toAdd() {
        return "/maintenance/checkRecord/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:checkRecord:edit")
    public String toEdit(@PathVariable("id") CheckRecord checkRecord, Model model) {
        model.addAttribute("checkRecord", checkRecord);
        return "/maintenance/checkRecord/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:checkRecord:add", "maintenance:checkRecord:edit"})
    @ResponseBody
    public ResultVo save(@Validated CheckRecordValid valid, CheckRecord checkRecord) {
        // 复制保留无需修改的数据
        if (checkRecord.getId() != null) {
            CheckRecord beCheckRecord = checkRecordService.getById(checkRecord.getId());
            EntityBeanUtil.copyProperties(beCheckRecord, checkRecord);
        }

        // 保存数据
        checkRecordService.save(checkRecord);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:checkRecord:detail")
    public String toDetail(@PathVariable("id") CheckRecord checkRecord, Model model) {
        model.addAttribute("checkRecord",checkRecord);
        return "/maintenance/checkRecord/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:checkRecord:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (checkRecordService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}