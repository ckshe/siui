package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Feeder;
import com.linln.admin.base.service.FeederService;
import com.linln.admin.base.validator.FeederValid;
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
 * @date 2020/05/14
 */
@Controller
@RequestMapping("/base/feeder")
public class FeederController {

    @Autowired
    private FeederService feederService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:feeder:index")
    public String index(Model model, Feeder feeder) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Feeder> example = Example.of(feeder, matcher);
        Page<Feeder> list = feederService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/feeder/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:feeder:add")
    public String toAdd() {
        return "/base/feeder/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:feeder:edit")
    public String toEdit(@PathVariable("id") Feeder feeder, Model model) {
        model.addAttribute("feeder", feeder);
        return "/base/feeder/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:feeder:add", "base:feeder:edit"})
    @ResponseBody
    public ResultVo save(@Validated FeederValid valid, Feeder feeder) {
        // 复制保留无需修改的数据
        if (feeder.getId() != null) {
            Feeder beFeeder = feederService.getById(feeder.getId());
            EntityBeanUtil.copyProperties(beFeeder, feeder);
        }

        // 保存数据
        feederService.save(feeder);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 使用次数清零
     * @param feeder
     * @return
     */
    @GetMapping("/zero/{id}")
    @ResponseBody
    public ResultVo zero(@PathVariable("id") Feeder feeder){
        if (feeder.getId() != null){
            Feeder feed = feederService.getById(feeder.getId());
            feed.setUse_times(0);
        }
        feederService.save(feeder);
        return ResultVoUtil.ZERO_SUCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:feeder:detail")
    public String toDetail(@PathVariable("id") Feeder feeder, Model model) {
        model.addAttribute("feeder",feeder);
        return "/base/feeder/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:feeder:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (feederService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }


}