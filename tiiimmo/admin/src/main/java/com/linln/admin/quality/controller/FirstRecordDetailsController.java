package com.linln.admin.quality.controller;

import com.linln.admin.quality.domain.FirstRecordDetails;
import com.linln.admin.quality.service.FirstRecordDetailsService;
import com.linln.admin.quality.validator.FirstRecordDetailsValid;
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
@RequestMapping("/quality/firstRecordDetails")
public class FirstRecordDetailsController {

    @Autowired
    private FirstRecordDetailsService firstRecordDetailsService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("quality:firstRecordDetails:index")
    public String index(Model model, FirstRecordDetails firstRecordDetails) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("link", match -> match.contains());

        // 获取数据列表
        Example<FirstRecordDetails> example = Example.of(firstRecordDetails, matcher);
        Page<FirstRecordDetails> list = firstRecordDetailsService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/quality/firstRecordDetails/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("quality:firstRecordDetails:add")
    public String toAdd() {
        return "/quality/firstRecordDetails/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("quality:firstRecordDetails:edit")
    public String toEdit(@PathVariable("id") FirstRecordDetails firstRecordDetails, Model model) {
        model.addAttribute("firstRecordDetails", firstRecordDetails);
        return "/quality/firstRecordDetails/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"quality:firstRecordDetails:add", "quality:firstRecordDetails:edit"})
    @ResponseBody
    public ResultVo save(@Validated FirstRecordDetailsValid valid, FirstRecordDetails firstRecordDetails) {
        // 复制保留无需修改的数据
        if (firstRecordDetails.getId() != null) {
            FirstRecordDetails beFirstRecordDetails = firstRecordDetailsService.getById(firstRecordDetails.getId());
            EntityBeanUtil.copyProperties(beFirstRecordDetails, firstRecordDetails);
        }

        // 保存数据
        firstRecordDetailsService.save(firstRecordDetails);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("quality:firstRecordDetails:detail")
    public String toDetail(@PathVariable("id") FirstRecordDetails firstRecordDetails, Model model) {
        model.addAttribute("firstRecordDetails",firstRecordDetails);
        return "/quality/firstRecordDetails/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("quality:firstRecordDetails:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (firstRecordDetailsService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}