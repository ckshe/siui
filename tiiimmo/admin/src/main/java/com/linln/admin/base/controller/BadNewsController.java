package com.linln.admin.base.controller;

import com.linln.admin.base.domain.BadNews;
import com.linln.admin.base.service.BadNewsService;
import com.linln.admin.base.validator.BadNewsValid;
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
 * @date 2020/05/13
 */
@Controller
@RequestMapping("/base/badNews")
public class BadNewsController {

    @Autowired
    private BadNewsService badNewsService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("badNews:badNews:index")
    public String index(Model model, BadNews badNews) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("bad_code", match -> match.contains())
                .withMatcher("bad_name", match -> match.contains());

        // 获取数据列表
        Example<BadNews> example = Example.of(badNews, matcher);
        Page<BadNews> list = badNewsService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/badNews/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("badNews:badNews:add")
    public String toAdd() {
        return "/base/badNews/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("badNews:badNews:edit")
    public String toEdit(@PathVariable("id") BadNews badNews, Model model) {
        model.addAttribute("badNews", badNews);
        return "/base/badNews/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"badNews:badNews:add", "badNews:badNews:edit"})
    @ResponseBody
    public ResultVo save(@Validated BadNewsValid valid, BadNews badNews) {
        // 复制保留无需修改的数据
        if (badNews.getId() != null) {
            BadNews beBadNews = badNewsService.getById(badNews.getId());
            EntityBeanUtil.copyProperties(beBadNews, badNews);
        }

        // 保存数据
        badNewsService.save(badNews);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("badNews:badNews:detail")
    public String toDetail(@PathVariable("id") BadNews badNews, Model model) {
        model.addAttribute("badNews",badNews);
        return "/base/badNews/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("badNews:badNews:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (badNewsService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}