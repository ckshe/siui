package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Line;
import com.linln.admin.base.service.LineService;
import com.linln.admin.base.service.PlantAreaService;
import com.linln.admin.base.validator.LineValid;
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
 * @author ww
 * @date 2020/05/14
 */
@Controller
@RequestMapping("/base/line")
public class LineController {

    @Autowired
    private LineService lineService;
    @Autowired
    private PlantAreaService plantAreaService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("line:line:index")
    public String index(Model model, Line line) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Line> example = Example.of(line, matcher);
        Page<Line> list = lineService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/line/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("line:line:add")
    public String toAdd(Model model) {
        //model.addAttribute("plantAreaList",plantAreaService.list());
        return "/base/line/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("line:line:edit")
    public String toEdit(@PathVariable("id") Line line, Model model) {
        model.addAttribute("line", line);
        return "/base/line/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"line:line:add", "line:line:edit"})
    @ResponseBody
    public ResultVo save(@Validated LineValid valid, Line line) {
        // 复制保留无需修改的数据
        if (line.getId() != null) {
            Line beLine = lineService.getById(line.getId());
            EntityBeanUtil.copyProperties(beLine, line);
        }

        // 保存数据
        lineService.save(line);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("line:line:detail")
    public String toDetail(@PathVariable("id") Line line, Model model) {
        model.addAttribute("line",line);
        return "/base/line/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("line:line:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (lineService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    @GetMapping("/findLine")
    @ResponseBody
    public ResultVo findLine(){
        List<Line> lines = lineService.list();
        if (lines!= null){
            return ResultVoUtil.success("查询成功",lines);
        } else {
            return ResultVoUtil.error(400,"查询失败");
        }

    }
}