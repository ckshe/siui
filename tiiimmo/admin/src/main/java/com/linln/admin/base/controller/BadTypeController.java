package com.linln.admin.base.controller;

import com.linln.admin.base.domain.BadType;
import com.linln.admin.base.service.BadTypeService;
import com.linln.admin.base.validator.BadTypeValid;
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
@RequestMapping("/base/badType")
public class BadTypeController {

    @Autowired
    private BadTypeService badTypeService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("badType:badType:index")
    public String index(Model model, BadType badType) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<BadType> example = Example.of(badType, matcher);
        Page<BadType> list = badTypeService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/badType/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("badType:badType:add")
    public String toAdd() {
        return "/base/badType/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("badType:badType:edit")
    public String toEdit(@PathVariable("id") BadType badType, Model model) {
        model.addAttribute("badType", badType);
        return "/base/badType/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"badType:badType:add", "badType:badType:edit"})
    @ResponseBody
    public ResultVo save(@Validated BadTypeValid valid, BadType badType) {
        // 复制保留无需修改的数据
        if (badType.getId() != null) {
            BadType beBadType = badTypeService.getById(badType.getId());
            EntityBeanUtil.copyProperties(beBadType, badType);
        }

        // 保存数据
        badTypeService.save(badType);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("badType:badType:detail")
    public String toDetail(@PathVariable("id") BadType badType, Model model) {
        model.addAttribute("badType",badType);
        return "/base/badType/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("badType:badType:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (badTypeService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }


    @GetMapping("/findBadType")
    @ResponseBody
    public ResultVo findLine(){
        List<BadType> badTypes = badTypeService.list();
        if (badTypes!= null){
            return ResultVoUtil.success("查询成功",badTypes);
        } else {
            return ResultVoUtil.error(400,"查询失败");
        }

    }


}