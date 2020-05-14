package com.linln.admin.base.controller;

import com.linln.admin.base.domain.Pcbdetail;
import com.linln.admin.base.service.PcbdetailService;
import com.linln.admin.base.validator.PcbdetailValid;
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
@RequestMapping("/base/pcbdetail")
public class PcbdetailController {

    @Autowired
    private PcbdetailService pcbdetailService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("pcb:pcbdetail:index")
    public String index(Model model, Pcbdetail pcbdetail) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("product_code", match -> match.contains())
                .withMatcher("product_name", match -> match.contains());

        // 获取数据列表
        Example<Pcbdetail> example = Example.of(pcbdetail, matcher);
        Page<Pcbdetail> list = pcbdetailService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/pcbdetail/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("pcb:pcbdetail:add")
    public String toAdd() {
        return "/base/pcbdetail/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("pcb:pcbdetail:edit")
    public String toEdit(@PathVariable("id") Pcbdetail pcbdetail, Model model) {
        model.addAttribute("pcbdetail", pcbdetail);
        return "/base/pcbdetail/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"pcb:pcbdetail:add", "pcb:pcbdetail:edit"})
    @ResponseBody
    public ResultVo save(@Validated PcbdetailValid valid, Pcbdetail pcbdetail) {
        // 复制保留无需修改的数据
        if (pcbdetail.getId() != null) {
            Pcbdetail bePcbdetail = pcbdetailService.getById(pcbdetail.getId());
            EntityBeanUtil.copyProperties(bePcbdetail, pcbdetail);
        }

        // 保存数据
        pcbdetailService.save(pcbdetail);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("pcb:pcbdetail:detail")
    public String toDetail(@PathVariable("id") Pcbdetail pcbdetail, Model model) {
        model.addAttribute("pcbdetail",pcbdetail);
        return "/base/pcbdetail/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("pcb:pcbdetail:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (pcbdetailService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}