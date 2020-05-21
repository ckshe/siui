package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.SilkRecord;
import com.linln.admin.maintenance.service.SilkRecordService;
import com.linln.admin.maintenance.validator.SilkRecordValid;
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
 * @date 2020/05/21
 */
@Controller
@RequestMapping("/maintenance/silkRecord")
public class SilkRecordController {

    @Autowired
    private SilkRecordService silkRecordService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:silkRecord:index")
    public String index(Model model, SilkRecord silkRecord) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<SilkRecord> example = Example.of(silkRecord, matcher);
        Page<SilkRecord> list = silkRecordService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/silkRecord/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:silkRecord:add")
    public String toAdd() {
        return "/maintenance/silkRecord/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:silkRecord:edit")
    public String toEdit(@PathVariable("id") SilkRecord silkRecord, Model model) {
        model.addAttribute("silkRecord", silkRecord);
        return "/maintenance/silkRecord/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:silkRecord:add", "maintenance:silkRecord:edit"})
    @ResponseBody
    public ResultVo save(@Validated SilkRecordValid valid, SilkRecord silkRecord) {
        // 复制保留无需修改的数据
        if (silkRecord.getId() != null) {
            SilkRecord beSilkRecord = silkRecordService.getById(silkRecord.getId());
            EntityBeanUtil.copyProperties(beSilkRecord, silkRecord);
        }

        // 保存数据
        silkRecordService.save(silkRecord);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:silkRecord:detail")
    public String toDetail(@PathVariable("id") SilkRecord silkRecord, Model model) {
        model.addAttribute("silkRecord",silkRecord);
        return "/maintenance/silkRecord/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:silkRecord:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (silkRecordService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}