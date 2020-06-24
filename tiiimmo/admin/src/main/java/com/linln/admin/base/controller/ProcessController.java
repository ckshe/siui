package com.linln.admin.base.controller;

import com.linln.admin.base.domain.ClassInfo;
import com.linln.admin.base.domain.Process;
import com.linln.admin.base.service.ProcessService;
import com.linln.admin.base.util.ApiResponse;
import com.linln.admin.base.validator.ProcessValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
//import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
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
@RequestMapping("/base/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("process:process:index")
    public String index(Model model, Process process) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Process> example = Example.of(process, matcher);
        Page<Process> list = processService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/base/process/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("process:process:add")
    public String toAdd() {
        return "/base/process/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("process:process:edit")
    public String toEdit(@PathVariable("id") Process process, Model model) {
        model.addAttribute("process", process);
        return "/base/process/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"process:process:add", "process:process:edit"})
    @ResponseBody
    public ResultVo save(@Validated ProcessValid valid, Process process) {
        // 复制保留无需修改的数据
        if (process.getId() != null) {
            Process beProcess = processService.getById(process.getId());
            EntityBeanUtil.copyProperties(beProcess, process);
        }

        // 保存数据
        processService.save(process);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("process:process:detail")
    public String toDetail(@PathVariable("id") Process process, Model model) {
        model.addAttribute("process",process);
        return "/base/process/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("process:process:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (processService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    /**
     * 排序更新
     * @param sort
     * @param id
     * @return
     */
    @RequestMapping("/updateSort")
    @ResponseBody
    public ApiResponse updateSort(String sort, Long id) {

        try {
            if ("down".equals(sort)) {
                processService.moveDown(id);
            } else if ("up".equals(sort)) {
                processService.moveUp(id);
            }
            return ApiResponse.ofSuccess("更新成功");
        } catch (Exception e) {
            return ApiResponse.ofError("第一条数据不能上移,最后一条数据不能下移");
        }
    }

    @GetMapping("/findProcessType")
    @ResponseBody
    public ResultVo findProcessType(){

        List<String> processes = processService.queryProcessType();
        if (processes!= null){
            return ResultVoUtil.success("查询成功",processes);
        } else {
            return ResultVoUtil.error(400,"查询失败");
        }

    }

    @GetMapping("/findProcess")
    @ResponseBody
    public ResultVo findProcess(){
        List<Process> processes = processService.list();
        if (processes!= null){
            return ResultVoUtil.success("查询成功",processes);
        } else {
            return ResultVoUtil.error(400,"查询失败");
        }

    }



}