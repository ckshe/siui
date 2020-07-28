package com.linln.admin.produce.controller;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.RespAndReqs.responce.PlateNoInfo;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.ProcessTaskDetail;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.admin.produce.service.ProcessTaskService;
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

    @Autowired
    private ProcessTaskService processTaskService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("produce:pcbTask:index")
    public String index(Model model, PcbTask pcbTask) {

        /*// 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_task_code", match -> match.contains())
                .withMatcher("task_sheet_code", match -> match.contains())
                .withMatcher("task_sheet_date", match -> match.contains())
                .withMatcher("temp_status_useless",match -> match.contains());
        pcbTask.setTemp_status_useless("下达");
        // 获取数据列表
        Example<PcbTask> example = Example.of(pcbTask, matcher);
        Page<PcbTask> list = pcbTaskService.getPageList(example);


        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);*/
        return "/produce/pcbTask/index";
    }


    @GetMapping("/indexFeeding")
    //@RequiresPermissions("produce:pcbTask:index")
    public String indexFeeding(Model model, PcbTask pcbTask) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcb_task_code", match -> match.contains())
                .withMatcher("task_sheet_code", match -> match.contains())
                .withMatcher("task_sheet_date", match -> match.contains())
                .withMatcher("pcb_task_status",match -> match.contains());
        pcbTask.setPcb_task_status("确认");
        // 获取数据列表
        Example<PcbTask> example = Example.of(pcbTask, matcher);
        Page<PcbTask> list = pcbTaskService.getPageList(example);

        //同步领料单
        for(PcbTask pcbTask2 : list.getContent()){
            if(pcbTask2.getPcb_plate_id()!=null&&!pcbTask2.getPcb_plate_id().equals("")){
                continue;
            }
            ResultVo resultVo = pcbTaskService.getFeedingTaskFromERP(pcbTask2.getPcb_task_code());
            String qtl = resultVo.getMsg();
            qtl = qtl==null||"".equals(qtl)?"0":qtl;
            String lightPlateno = (String)resultVo.getData();
            pcbTask2.setPcb_plate_id(lightPlateno);
            pcbTask2.setQi_tao_lv(qtl);
            pcbTaskService.save(pcbTask2);

        }

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/produce/pcbTask/indexFeeding";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("produce:pcbTask:add")
    public String toAdd() {
        return "/produce/pcbTask/add";
    }

    @GetMapping("/plan")
    public String plan() {
        return "/produce/pcbTask/plan";
    }

    @GetMapping("/planDetail")
    public String planDetail() {
        return "/produce/pcbTask/planDetail";
    }

    @GetMapping("/distributionDetail")
    public String distributionDetail() {
        return "/produce/pcbTask/distributionDetail";
    }

    @GetMapping("/pickDetail")
    public String pickDetail() {
        return "/produce/pcbTask/pickDetail";
    }

    @GetMapping("/beiliao")
    public String beiliao() {return "/produce/beiliao/index"; }

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
    //同步排产计划
    @ResponseBody
    @GetMapping("/getPcbTaskFromERP")
    public ResultVo getPcbTaskFromERP( ){
        return pcbTaskService.getPcbTaskFromERP(null);
    }
    //同步投料计划
    @ResponseBody
    @GetMapping("/getFeedingTaskFromERP")
    public ResultVo getFeedingTaskFromERP( ){
        return pcbTaskService.getFeedingTaskFromERP(null);
    }

    //根据排产计划id进行投产生成工序计划
    @GetMapping("/putIntoProduceBefore/{id}")
    @ResponseBody
    public ResultVo putIntoProduceBefore(@PathVariable Long id){

        return pcbTaskService.putIntoProduceBefore(id);
    }

    //根据排产计划id生成板编号信息
    @GetMapping("/generateBatchId/{id}")
    @ResponseBody
    public ResultVo generateBatchId(@PathVariable Long id){
        return pcbTaskService.generateBatchId(id);
    }

    @PostMapping("/putIntoProduceByPlateInfo")
    @ResponseBody
    public ResultVo putIntoProduceByPlateInfo(@RequestBody PlateNoInfo plateNoInfo){
        return pcbTaskService.putIntoProduceByPlateInfo(plateNoInfo);
    }


    //根据排产计划id获取工序计划
    @GetMapping("/findProcessTaskByPCBTaskId/{id}")
    @ResponseBody
    public ResultVo findProcessTaskByPCBTaskId(@PathVariable Long id){
        return pcbTaskService.findProcessTaskByPCBTaskId(id);
    }

    //更新排产工序计划
    @PostMapping("/updateProcessTask")
    @ResponseBody
    public ResultVo updateProcessTask(@RequestBody ProcessTaskReq processTaskReq){

        return pcbTaskService.updateProcessTask(processTaskReq);
    }

    @PostMapping("/updateProcess/{id}")
    @ResponseBody
    public ResultVo updateProcess(ProcessTask processTask){
        return pcbTaskService.updateProcess(processTask);
    }

    //工序计划下达到机台
    @PostMapping("/putIntoProduce")
    @ResponseBody
    public ResultVo putIntoProduce(@RequestBody PcbTaskReq req){
        return pcbTaskService.putIntoProduce(req);
    }

    //获取备料工序计划
    @PostMapping("/findProcessTaskByProcessName")
    @ResponseBody
    public ResultVo findProcessTaskByProcessName(@RequestBody PcbTaskReq req){
        return pcbTaskService.findProcessTaskByProcessName(req);
    }

    //获取排产计划
    @PostMapping("/findScheduling")
    @ResponseBody
    public ResultVo findScheduling(@RequestBody PcbTaskReq req){
        return pcbTaskService.findScheduling(req);
    }


    //查询投料单
    @PostMapping("/findFeedingTask")
    @ResponseBody
    public ResultVo findFeedingTask(@RequestBody PcbTaskReq req){
        return pcbTaskService.findFeedingTask(req);
    }


    //机台查询当前工单
    @PostMapping("/findProcessTaskByDevice")
    @ResponseBody
    public ResultVo findProcessTaskByDevice(@RequestBody PcbTaskReq req){
        return pcbTaskService.findProcessTaskByDevice(req);
    }


    //工序任务单状态修改
    @PostMapping("/modifyProcessTaskStatus")
    @ResponseBody
    public ResultVo modifyProcessTaskStatus(@RequestBody PcbTaskReq req){
        return pcbTaskService.modifyProcessTaskStatus2(req);
    }


    //工序任务日计划当天结算
    @PostMapping("/settlementDetailCount")
    @ResponseBody
    public ResultVo settlementDetailCount(@RequestBody PcbTaskReq req){
        return pcbTaskService.settlementDetailCount(req);
    }

    //工单计数
    @PostMapping("/countProcessTaskAmount")
    @ResponseBody
    public ResultVo countProcessTaskAmount(@RequestBody PcbTaskReq req){
        return pcbTaskService.countProcessTaskAmount(req);
    }


    //不良录入
    @PostMapping("/recordBadTypeList")
    @ResponseBody
    public ResultVo recordBadTypeList(@RequestBody PcbTaskReq req){
        return pcbTaskService.recordBadTypeList(req);
    }

    //查询不良录入
    @PostMapping("/findBadTypeRecordList")
    @ResponseBody
    public ResultVo findBadTypeRecordList(@RequestBody PcbTaskReq req){
        return pcbTaskService.findBadTypeRecordList(req);
    }
    //根据排产计划编号查询所有板编号
    @PostMapping("/findPcbTaskPlateNo")
    @ResponseBody
    public ResultVo findPcbTaskPlateNo(@RequestBody PcbTaskReq req){
        return pcbTaskService.findPcbTaskPlateNo(req);
    }

    //平板生产数接口.
    @PostMapping("/deviceAmountAndworkTime")
    @ResponseBody
    public ResultVo deviceAmountAndworkTime(@RequestBody PcbTaskReq req){
        return pcbTaskService.deviceAmountAndworkTime(req);
    }

    /**
     * 工序计划列表页面
     */
    @GetMapping("/processTask")
    public String processTask() {

        return "/produce/pcbTask/processTask";
    }

    //获取所有已投产状态的工序计划
    @PostMapping("/findProcessTask")
    @ResponseBody
    public ResultVo findProcessTask(@RequestBody ProcessTaskReq req){
        return processTaskService.findProcessTask(req);
    }

    @GetMapping("/checkIsLogin")
    @ResponseBody
    public ResultVo checkIsLogin(){
        return ResultVoUtil.success("");
    }


    //获取所有已投产状态的工序计划
    @PostMapping("/findProcessTaskDeatilList")
    @ResponseBody
    public ResultVo findProcessTaskDeatilList(@RequestBody ProcessTaskReq req){
        List<ProcessTaskDetail> detailList = processTaskService.findProcessTaskDeatilList(req.getProcess_task_code());
        return ResultVoUtil.success(detailList);
    }


    //新增工序计划日计划
    @PostMapping("/addTaskDetailList")
    @ResponseBody
    public ResultVo addTaskDetailList(@RequestBody ProcessTaskReq req){
        processTaskService.addTaskDetailList(req);
        return ResultVoUtil.success();
    }

    @GetMapping("/deleteProcessTaskDetailById/{id}")
    @ResponseBody
    public ResultVo deleteProcessTaskDetailById(@PathVariable Long id){
        processTaskService.deleteProcessTaskDetailById(id);
        return ResultVoUtil.success("删除成功");
    }



}