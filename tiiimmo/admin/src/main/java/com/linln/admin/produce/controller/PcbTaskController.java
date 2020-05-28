package com.linln.admin.produce.controller;

import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.admin.produce.validator.PcbTaskValid;
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
 * @date 2020/05/18
 */
@Controller
@RequestMapping("/produce/pcbTask")
public class PcbTaskController {

    @Autowired
    private PcbTaskService pcbTaskService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("produce:pcbTask:index")
    public String index(Model model, PcbTask pcbTask) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_task_code", match -> match.contains())
                .withMatcher("task_sheet_code", match -> match.contains())
                .withMatcher("task_sheet_date", match -> match.contains());

        // 获取数据列表
        Example<PcbTask> example = Example.of(pcbTask, matcher);
        Page<PcbTask> list = pcbTaskService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/produce/pcbTask/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("produce:pcbTask:add")
    public String toAdd() {
        return "/produce/pcbTask/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("produce:pcbTask:edit")
    public String toEdit(@PathVariable("id") PcbTask pcbTask, Model model) {
        model.addAttribute("pcbTask", pcbTask);
        return "/produce/pcbTask/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"produce:pcbTask:add", "produce:pcbTask:edit"})
    @ResponseBody
    public ResultVo save(@Validated PcbTaskValid valid, PcbTask pcbTask) {
        // 复制保留无需修改的数据
        if (pcbTask.getId() != null) {
            PcbTask bePcbTask = pcbTaskService.getById(pcbTask.getId());
            EntityBeanUtil.copyProperties(bePcbTask, pcbTask);
        }

        // 保存数据
        pcbTaskService.save(pcbTask);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("produce:pcbTask:detail")
    public String toDetail(@PathVariable("id") PcbTask pcbTask, Model model) {
        model.addAttribute("pcbTask",pcbTask);
        return "/produce/pcbTask/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("produce:pcbTask:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (pcbTaskService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }

    @ResponseBody
    @GetMapping("/getPcbTaskFromERP")
    public ResultVo getPcbTaskFromERP(){
        return pcbTaskService.getPcbTaskFromERP(null);
    }


    @GetMapping("/putIntoProduceBefore")
    @ResponseBody
    public ResultVo putIntoProduceBefore(){
        return pcbTaskService.putIntoProduceBefore(null);
    }

    @GetMapping("/findProcessTaskByPCBTaskId")
    @ResponseBody
    public ResultVo findProcessTaskByPCBTaskId(){
        return pcbTaskService.findProcessTaskByPCBTaskId(31L);
    }
}