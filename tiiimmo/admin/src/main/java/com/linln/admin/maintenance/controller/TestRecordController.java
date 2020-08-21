package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.TestRecord;
import com.linln.admin.maintenance.service.TestRecordService;
import com.linln.admin.maintenance.validator.TestRecordValid;
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
@RequestMapping("/maintenance/testRecord")
public class TestRecordController {

    @Autowired
    private TestRecordService testRecordService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:testRecord:index")
    public String index(Model model, TestRecord testRecord) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("deviceCode", match -> match.contains());

        // 获取数据列表
        Example<TestRecord> example = Example.of(testRecord, matcher);
        Page<TestRecord> list = testRecordService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/testRecord/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:testRecord:add")
    public String toAdd() {
        return "/maintenance/testRecord/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:testRecord:edit")
    public String toEdit(@PathVariable("id") TestRecord testRecord, Model model) {
        model.addAttribute("testRecord", testRecord);
        return "/maintenance/testRecord/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:testRecord:add", "maintenance:testRecord:edit"})
    @ResponseBody
    public ResultVo save(@Validated TestRecordValid valid, TestRecord testRecord) {
        // 复制保留无需修改的数据
        if (testRecord.getId() != null) {
            TestRecord beTestRecord = testRecordService.getById(testRecord.getId());
            EntityBeanUtil.copyProperties(beTestRecord, testRecord);
        }

        // 保存数据
        testRecordService.save(testRecord);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:testRecord:detail")
    public String toDetail(@PathVariable("id") TestRecord testRecord, Model model) {
        model.addAttribute("testRecord",testRecord);
        return "/maintenance/testRecord/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:testRecord:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (testRecordService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
    @GetMapping("/detail")
    public String distributionDetail() {
        return "/maintenance/testRecord/detail";
    }
}