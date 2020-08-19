package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.RepairRecord;
import com.linln.admin.maintenance.service.RepairRecordService;
import com.linln.admin.maintenance.validator.RepairRecordValid;
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
@RequestMapping("/maintenance/repairRecord")
public class RepairRecordController {

    @Autowired
    private RepairRecordService repairRecordService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:repairRecord:index")
    public String index(Model model, RepairRecord repairRecord) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("repairDevice", match -> match.contains())
                .withMatcher("repairUser", match -> match.contains());

        // 获取数据列表
        Example<RepairRecord> example = Example.of(repairRecord, matcher);
        Page<RepairRecord> list = repairRecordService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/repairRecord/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:repairRecord:add")
    public String toAdd() {
        return "/maintenance/repairRecord/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:repairRecord:edit")
    public String toEdit(@PathVariable("id") RepairRecord repairRecord, Model model) {
        model.addAttribute("repairRecord", repairRecord);
        return "/maintenance/repairRecord/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:repairRecord:add", "maintenance:repairRecord:edit"})
    @ResponseBody
    public ResultVo save(@Validated RepairRecordValid valid, RepairRecord repairRecord) {
        // 复制保留无需修改的数据
        if (repairRecord.getId() != null) {
            RepairRecord beRepairRecord = repairRecordService.getById(repairRecord.getId());
            EntityBeanUtil.copyProperties(beRepairRecord, repairRecord);
        }

        // 保存数据
        repairRecordService.save(repairRecord);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:repairRecord:detail")
    public String toDetail(@PathVariable("id") RepairRecord repairRecord, Model model) {
        model.addAttribute("repairRecord",repairRecord);
        return "/maintenance/repairRecord/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:repairRecord:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (repairRecordService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}