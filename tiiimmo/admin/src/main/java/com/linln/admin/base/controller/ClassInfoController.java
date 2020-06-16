package com.linln.admin.base.controller;

import com.linln.admin.base.domain.ClassInfo;
import com.linln.admin.base.service.ClassInfoService;
import com.linln.admin.base.validator.ClassInfoValid;
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
@RequestMapping("/base/classInfo")
public class ClassInfoController {

    @Autowired
    private ClassInfoService classInfoService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:classInfo:index")
    public String index(Model model, ClassInfo classInfo) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<ClassInfo> example = Example.of(classInfo, matcher);
        Page<ClassInfo> list = classInfoService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/classInfo/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:classInfo:add")
    public String toAdd() {
        return "/base/classInfo/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:classInfo:edit")
    public String toEdit(@PathVariable("id") ClassInfo classInfo, Model model) {
        model.addAttribute("classInfo", classInfo);
        return "/base/classInfo/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:classInfo:add", "base:classInfo:edit"})
    @ResponseBody
    public ResultVo save(@Validated ClassInfoValid valid, ClassInfo classInfo) {
        // 复制保留无需修改的数据
        if (classInfo.getId() != null) {
            ClassInfo beClassInfo = classInfoService.getById(classInfo.getId());
            EntityBeanUtil.copyProperties(beClassInfo, classInfo);
        }

        // 保存数据
        classInfoService.save(classInfo);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:classInfo:detail")
    public String toDetail(@PathVariable("id") ClassInfo classInfo, Model model) {
        model.addAttribute("classInfo",classInfo);
        return "/base/classInfo/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:classInfo:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (classInfoService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    @GetMapping("/findClassInfo")
    @ResponseBody
    public ResultVo findClassInfo(){

        List<ClassInfo> classInfos = classInfoService.list();
        if (classInfos!= null){
            return ResultVoUtil.success("查询成功",classInfos,classInfoService.getSize().intValue());
        } else {
            return ResultVoUtil.error(400,"查询失败");
        }

    }
}