package com.linln.admin.produce.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linln.RespAndReqs.ScheduleJobReq;
import com.linln.RespAndReqs.TaskPutIntoReq;
import com.linln.admin.base.domain.Process;
import com.linln.admin.base.repository.ModelsRepository;
import com.linln.admin.base.repository.ProcessRepository;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.ScheduleJobApi;
import com.linln.admin.produce.repository.PcbTaskRepository;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.produce.repository.ScheduleJobApiRepository;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.ApiUtil;
import com.linln.utill.ReadUtill;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
@Service
public class PcbTaskServiceImpl implements PcbTaskService {

    @Autowired
    private PcbTaskRepository pcbTaskRepository;

    @Autowired
    private ModelsRepository modelsRepository;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ScheduleJobApiRepository scheduleJobApiRepository;


    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public PcbTask getById(Long id) {
        return pcbTaskRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<PcbTask> getPageList(Example<PcbTask> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return pcbTaskRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param pcbTask 实体对象
     */
    @Override
    public PcbTask save(PcbTask pcbTask) {
        return pcbTaskRepository.save(pcbTask);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return pcbTaskRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public ResultVo getDataFromERP(String dataBetween) {

        ScheduleJobApi jobApi = scheduleJobApiRepository.findAllByApiName("stkd_PCWLRCL");
        ScheduleJobReq scheduleJobReq = new ScheduleJobReq();
        scheduleJobReq.setDesc(jobApi.getRemark() == null ? "" : jobApi.getRemark());
        scheduleJobReq.setKey(jobApi.getKey() == null ? "" : jobApi.getKey());
        scheduleJobReq.setWhere(jobApi.getCondition() == null ? "" : jobApi.getCondition());
        scheduleJobReq.setAction(jobApi.getApiName() == null ? "" : jobApi.getApiName());
        JSONArray lists = ApiUtil.postToScheduleJobApi(jobApi.getApiUrl(),scheduleJobReq);

        //String path = "D:\\workspace\\timosecond\\tiiimmo\\admin\\src\\main\\resources\\task.json";

       // String s = ReadUtill.readJsonFile(path);
       // JSONObject jobj = JSON.parseObject(s);
        //JSONArray lists = jobj.getJSONArray("data");
        List<PcbTask> pckTaskList = new ArrayList<>();
        for(int i = 0 ; i<lists.size();i++){
            JSONObject param = lists.getJSONObject(i);
            String task_sheet_id = param.getString("制造编号");
            Date produce_plan_date =  param.getDate("计划投产时间");
            String pcb_task_id = param.getString("任务号");
            String pcb_name = param.getString("pcb_name");
            Date produce_plan_complete_date =  param.getDate("计划完成时间");
            Date produce_date =  param.getDate("实际投产时间");
            Date produce_complete_date =  param.getDate("生产完成时间");
            String model_name = param.getString("机型名称");
            String pcb_id = param.getString("PCB编码");
            String model_ver = param.getString("机型版本");
            String pcb_task_status = param.getString("工单状态");
            Integer pcb_quantity = param.getInteger("PCB数量");
            String feeding_code = param.getString("投料单号");
            String workshop = param.getString("车间");
            String is_rohs = param.getString("FRoHSBz");
            PcbTask pcbTask = new PcbTask();
            pcbTask.setProduce_plan_date(produce_plan_date);
            pcbTask.setTask_sheet_id(task_sheet_id);
            pcbTask.setIs_rohs(is_rohs);
            pcbTask.setPcb_task_id(pcb_task_id);
            pcbTask.setProduce_date(produce_date);
            pcbTask.setPcb_name(pcb_name);
            pcbTask.setProduce_plan_complete_date(produce_plan_complete_date);
            pcbTask.setProduce_complete_date(produce_complete_date);
            pcbTask.setModel_name(model_name);
            pcbTask.setPcb_id(pcb_id);
            pcbTask.setPcb_task_status(pcb_task_status);
            pcbTask.setModel_ver(model_ver);
            pcbTask.setFeeding_code(feeding_code);
            pcbTask.setPcb_quantity(pcb_quantity);
            pcbTask.setWorkshop(workshop);

            pckTaskList.add(pcbTask);
        }
        pcbTaskRepository.saveAll(pckTaskList);
        return ResultVoUtil.success("同步完成");
    }

    @Override
    public ResultVo putIntoProduceBefore(TaskPutIntoReq taskPutIntoReq) {
        PcbTask pcbTask = null;

        Optional<PcbTask> optional = pcbTaskRepository.findById(taskPutIntoReq.getPcbTaskId());
        pcbTask = optional.isPresent()?optional.get():null;
        if(pcbTask==null) {
            return ResultVoUtil.error("查不到该pcb任务单");
        }
        List<ProcessTask> processTaskList = new ArrayList<>();
        List<Process> processList = processRepository.findAllByStatus(StatusEnum.OK.getCode());
        for(Process p : processList){
            ProcessTask processTask = new ProcessTask();
            processTask.setPcb_task_id(pcbTask.getPcb_task_id());
            processTask.setIs_rohs(pcbTask.getIs_rohs());
            processTask.setPcb_quantity(pcbTask.getPcb_quantity());
            processTask.setModel_ver(pcbTask.getModel_ver());
            processTask.setProcess_name(p.getName());
            processTask.setPcb_name(pcbTask.getPcb_name());
            processTask.setProcess_task_status("未下达到机台");
            processTaskList.add(processTask);

        }
        processTaskRepository.saveAll(processTaskList);
        pcbTask.setPcb_task_status("已下达切分工序未投产");
        pcbTaskRepository.save(pcbTask);
        return  ResultVoUtil.success("下达切分完成");
    }

    @Override
    public ResultVo putIntoProduce(TaskPutIntoReq taskPutIntoReq) {

        ProcessTask processTask = null;
        Optional<ProcessTask> optional = processTaskRepository.findById(taskPutIntoReq.getProcessTaskId());;
        processTask = optional.isPresent()?optional.get():null;
        if(processTask==null){
            return ResultVoUtil.error("查找不到该工序计划");
        }
        processTask.setDevice_code(taskPutIntoReq.getDeviceCode());
        processTask.setDevice_name(taskPutIntoReq.getDeviceName());
        processTask.setStart_time(taskPutIntoReq.getStartTime());
        processTask.setFinish_time(taskPutIntoReq.getFinishTime());
        processTask.setProcess_task_status("已下达到机台");
        processTaskRepository.save(processTask);
        return ResultVoUtil.success("工序计划下达到机台成功");
    }
}