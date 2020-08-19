package com.linln.admin.maintenance.controller;

import com.linln.admin.maintenance.domain.SpotCheck;
import com.linln.admin.maintenance.service.SpotCheckService;
import com.linln.admin.maintenance.validator.SpotCheckValid;
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
 * @author 小懒虫
 * @date 2020/08/19
 */
@Controller
@RequestMapping("/maintenance/spotCheck")
public class SpotCheckController {

    @Autowired
    private SpotCheckService spotCheckService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("maintenance:spotCheck:index")
    public String index(Model model, SpotCheck spotCheck) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("checkDevice", match -> match.contains())
                .withMatcher("checkUser", match -> match.contains());

        // 获取数据列表
        Example<SpotCheck> example = Example.of(spotCheck, matcher);
        Page<SpotCheck> list = spotCheckService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/maintenance/spotCheck/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("maintenance:spotCheck:add")
    public String toAdd() {
        return "/maintenance/spotCheck/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("maintenance:spotCheck:edit")
    public String toEdit(@PathVariable("id") SpotCheck spotCheck, Model model) {
        model.addAttribute("spotCheck", spotCheck);
        return "/maintenance/spotCheck/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"maintenance:spotCheck:add", "maintenance:spotCheck:edit"})
    @ResponseBody
    public ResultVo save(@Validated SpotCheckValid valid, SpotCheck spotCheck) {
        // 复制保留无需修改的数据
        if (spotCheck.getId() != null) {
            SpotCheck beSpotCheck = spotCheckService.getById(spotCheck.getId());
            EntityBeanUtil.copyProperties(beSpotCheck, spotCheck);
        }

        // 保存数据
        spotCheckService.save(spotCheck);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("maintenance:spotCheck:detail")
    public String toDetail(@PathVariable("id") SpotCheck spotCheck, Model model) {
        model.addAttribute("spotCheck",spotCheck);
        return "/maintenance/spotCheck/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("maintenance:spotCheck:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (spotCheckService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}