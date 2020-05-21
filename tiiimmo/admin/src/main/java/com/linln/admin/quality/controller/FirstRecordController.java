package com.linln.admin.quality.controller;

import com.linln.admin.quality.domain.FirstRecord;
import com.linln.admin.quality.service.FirstRecordService;
import com.linln.admin.quality.validator.FirstRecordValid;
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
@RequestMapping("/quality/firstRecord")
public class FirstRecordController {

    @Autowired
    private FirstRecordService firstRecordService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("quality:firstRecord:index")
    public String index(Model model, FirstRecord firstRecord) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("manufactureNo", match -> match.contains());

        // 获取数据列表
        Example<FirstRecord> example = Example.of(firstRecord, matcher);
        Page<FirstRecord> list = firstRecordService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/quality/firstRecord/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("quality:firstRecord:add")
    public String toAdd() {
        return "/quality/firstRecord/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("quality:firstRecord:edit")
    public String toEdit(@PathVariable("id") FirstRecord firstRecord, Model model) {
        model.addAttribute("firstRecord", firstRecord);
        return "/quality/firstRecord/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"quality:firstRecord:add", "quality:firstRecord:edit"})
    @ResponseBody
    public ResultVo save(@Validated FirstRecordValid valid, FirstRecord firstRecord) {
        // 复制保留无需修改的数据
        if (firstRecord.getId() != null) {
            FirstRecord beFirstRecord = firstRecordService.getById(firstRecord.getId());
            EntityBeanUtil.copyProperties(beFirstRecord, firstRecord);
        }

        // 保存数据
        firstRecordService.save(firstRecord);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("quality:firstRecord:detail")
    public String toDetail(@PathVariable("id") FirstRecord firstRecord, Model model) {
        model.addAttribute("firstRecord",firstRecord);
        return "/quality/firstRecord/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("quality:firstRecord:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (firstRecordService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}