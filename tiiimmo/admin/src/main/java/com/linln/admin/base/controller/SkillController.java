package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Skill;
import com.linln.admin.base.service.SkillService;
import com.linln.admin.base.validator.SkillValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.xmlbeans.impl.common.ResolverUtil;
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
 * @date 2020/05/31
 */
@Controller
@RequestMapping("/base/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("base:skill:index")
    public String index(Model model, Skill skill) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("skill_code", match -> match.contains())
                .withMatcher("skill_name", match -> match.contains());

        // 获取数据列表
        Example<Skill> example = Example.of(skill, matcher);
        Page<Skill> list = skillService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/skill/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("base:skill:add")
    public String toAdd() {
        return "/base/skill/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("base:skill:edit")
    public String toEdit(@PathVariable("id") Skill skill, Model model) {
        model.addAttribute("skill", skill);
        return "/base/skill/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"base:skill:add", "base:skill:edit"})
    @ResponseBody
    public ResultVo save(@Validated SkillValid valid, Skill skill) {
        // 复制保留无需修改的数据
        if (skill.getId() != null) {
            Skill beSkill = skillService.getById(skill.getId());
            EntityBeanUtil.copyProperties(beSkill, skill);
        }

        // 保存数据
        skillService.save(skill);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("base:skill:detail")
    public String toDetail(@PathVariable("id") Skill skill, Model model) {
        model.addAttribute("skill",skill);
        return "/base/skill/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("base:skill:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (skillService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    @GetMapping("/findAllSkill")
    @ResponseBody
    public ResultVo findAllSkill(){
        List<Skill> list = skillService.findAllSkill();
        return ResultVoUtil.success(list);
    }


    @GetMapping("/findAllByRoleId")
    @ResponseBody
    public ResultVo findAllByRoleId(Long roleId){
        List<Skill> list = skillService.findAllByRoleId(roleId);
        return ResultVoUtil.success(list);
    }

}