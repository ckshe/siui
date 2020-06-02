package com.linln.admin.produce.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linln.RespAndReqs.ScheduleJobReq;
import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.admin.base.domain.Process;
import com.linln.admin.base.repository.ModelsRepository;
import com.linln.admin.base.repository.ProcessRepository;
import com.linln.admin.produce.domain.*;
import com.linln.admin.produce.repository.*;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.ApiUtil;
import com.linln.utill.ReadUtill;
import io.swagger.models.auth.In;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskDecorator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    private FeedingTaskRepository feedingTaskRepository;

    @Autowired
    private ProcessTaskDeviceRepository processTaskDeviceRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


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
    public ResultVo getPcbTaskFromERP(String dataBetween) {

        ScheduleJobApi jobApi = scheduleJobApiRepository.findAllByApiName("stkd_PCWLRCL");
        ScheduleJobReq scheduleJobReq = new ScheduleJobReq();
        scheduleJobReq.setDesc(jobApi.getRemark() == null ? "" : jobApi.getRemark());
        scheduleJobReq.setKey(jobApi.getKey() == null ? "" : jobApi.getKey());
        scheduleJobReq.setWhere(jobApi.getCondition() == null ? "" : jobApi.getCondition());
        scheduleJobReq.setAction(jobApi.getApiName() == null ? "" : jobApi.getApiName());
        //JSONArray lists = ApiUtil.postToScheduleJobApi(jobApi.getApiUrl(),scheduleJobReq);

        String path = "D:\\workspace\\timosecond\\tiiimmo\\admin\\src\\main\\resources\\task2.json";

        String s = ReadUtill.readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        JSONArray lists = jobj.getJSONArray("data");
        List<PcbTask> pckTaskList = new ArrayList<>();
        for(int i = 0 ; i<lists.size();i++){
            JSONObject param = lists.getJSONObject(i);
            //生产任务单号
            String pcb_task_code = param.getString("FRWFBillNo");
            //这里需要匹配是否已同步过的
            PcbTask pcbTask = new PcbTask();
            List<PcbTask> oldPcbTasks = pcbTaskRepository.findAllByPcb_task_code(pcb_task_code);
            if(oldPcbTasks!=null&&oldPcbTasks.size()!=0){
                pcbTask = oldPcbTasks.get(0);
                continue;
            }
            //制造编号
            String task_sheet_code = param.getString("FProduceNo");
            //计划投产时间
            Date produce_plan_date =  param.getDate("FPlanCommitDate");
            //pcb名称
            String pcb_name = param.getString("FPCBName");
            //计划完成时间
            Date produce_plan_complete_date =  param.getDate("FPlanFinishDate");
            //实际投产时间
            Date produce_date =  param.getDate("FStartDate");
            //生产完成时间
            Date produce_complete_date =  param.getDate("FFinishDate");
            //机型名称
            String model_name = param.getString("FProduceName");
            //pcb编码
            String pcb_id = param.getString("FPCBModel");
            //机型版本
            String model_ver = param.getString("FProduceModel");
            //工单状态
            String pcb_task_status = param.getString("FStatus");
            //pcb 生产数量
            Integer pcb_quantity = param.getInteger("FPCBQty");
            //投料单号
            String feeding_code = param.getString("FTLFBillno");
            //车间
            String workshop = param.getString("FWorkShop");
            //ROHS
            String is_rohs = param.getString("FRoHSBz");
            //通知日期
            Date noticeDate = param.getDate("FNoticeDate");
            pcbTask.setProduce_plan_date(produce_plan_date);
            pcbTask.setTask_sheet_code(task_sheet_code);
            pcbTask.setIs_rohs(is_rohs);
            pcbTask.setPcb_task_code(pcb_task_code);
            pcbTask.setProduce_date(produce_date);
            pcbTask.setTask_sheet_date(noticeDate);
            pcbTask.setPcb_name(pcb_name);
            pcbTask.setFactory("万吉厂区");
            pcbTask.setProduce_plan_complete_date(produce_plan_complete_date);
            pcbTask.setProduce_complete_date(produce_complete_date);
            pcbTask.setModel_name(model_name);
            pcbTask.setPcb_id(pcb_id);
            pcbTask.setPcb_task_status(pcb_task_status);
            pcbTask.setModel_ver(model_ver);
            pcbTask.setFeeding_task_code(feeding_code);
            pcbTask.setPcb_quantity(pcb_quantity);
            pcbTask.setWorkshop(workshop);
            //生成板编号

            //设备记录最后版编号

            //优先级默认1
            pcbTask.setPriority(1);
            pckTaskList.add(pcbTask);
        }
        pcbTaskRepository.saveAll(pckTaskList);
        return ResultVoUtil.success("同步完成");
    }


    @Override
    public ResultVo getFeedingTaskFromERP(String dataBetween) {
        String path = "D:\\workspace\\timosecond\\tiiimmo\\admin\\src\\main\\resources\\feeding.json";

        ScheduleJobApi jobApi = scheduleJobApiRepository.findAllByApiName("stkd_PCWLRCL");
        ScheduleJobReq scheduleJobReq = new ScheduleJobReq();
        scheduleJobReq.setKey(jobApi.getKey() == null ? "" : jobApi.getKey());
        scheduleJobReq.setDesc(jobApi.getRemark() == null ? "" : jobApi.getRemark());
        scheduleJobReq.setWhere(jobApi.getCondition() == null ? "" : jobApi.getCondition());
        scheduleJobReq.setAction(jobApi.getApiName() == null ? "" : jobApi.getApiName());
        //JSONArray lists = ApiUtil.postToScheduleJobApi(jobApi.getApiUrl(),scheduleJobReq);


        String s = ReadUtill.readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        List<FeedingTask> feedingTaskList = new ArrayList<>();
        JSONArray lists = jobj.getJSONArray("data");
        for(int i =0;i<lists.size();i++){
            JSONObject param = lists.getJSONObject(i);

            FeedingTask feedingTask = new FeedingTask();
            feedingTask.setProduct_code(param.getString("FNumber"));
            feedingTask.setProduct_name(param.getString("FName"));
            feedingTask.setSpecification_model(param.getString("FModel"));
            feedingTask.setStock_name(param.getString("FStock"));
            feedingTask.setUnit(param.getString("FUnit"));
            feedingTask.setFQtyScrap(param.getBigDecimal("FQtyScrap"));
            feedingTask.setFQtyMust(param.getBigDecimal("FQtyMust"));
            feedingTask.setFStockQty(param.getBigDecimal("FStockQty"));
            feedingTask.setFNStockQty(param.getBigDecimal("FNStockQty"));
            feedingTask.setFQty(param.getBigDecimal("FQty"));
            feedingTask.setFQtlv(param.getString("FQty"));
            feedingTask.setPcb_task_code(param.getString("FRWFBillno"));
            feedingTask.setFeeding_task_code(param.getString("FTLFBillno"));
            feedingTaskList.add(feedingTask);
        }
        feedingTaskRepository.saveAll(feedingTaskList);
        return ResultVoUtil.success("同步完成");

    }

    @Override
    public ResultVo putIntoProduceBefore(Long pcbTaskId) {
        PcbTask pcbTask = null;

        Optional<PcbTask> optional = pcbTaskRepository.findById(pcbTaskId);
        pcbTask = optional.isPresent()?optional.get():null;
        if(pcbTask==null) {
            return ResultVoUtil.error("查不到该pcb任务单");
        }
        List<ProcessTask> processTaskList = new ArrayList<>();
        List<Process> processList = processRepository.findAllByStatus(StatusEnum.OK.getCode());
        int i = 1;
        for(Process p : processList){
            ProcessTask processTask = new ProcessTask();
            processTask.setPcb_task_code(pcbTask.getPcb_task_code());
            processTask.setIs_rohs(pcbTask.getIs_rohs());
            processTask.setPcb_name(pcbTask.getPcb_name());
            processTask.setPcb_task_id(pcbTask.getId());
            processTask.setPcb_quantity(pcbTask.getPcb_quantity());
            processTask.setModel_ver(pcbTask.getModel_ver());
            processTask.setAmount_completed(0);
            processTask.setProcess_name(p.getName());
            processTask.setTask_sheet_code(pcbTask.getTask_sheet_code());
            processTask.setPcb_name(pcbTask.getPcb_name());
            processTask.setProcess_task_status("未下达到机台");
            String processTaskCode = "BL"+pcbTask.getPcb_task_code()+"_00"+i;
            processTask.setProcess_task_code(processTaskCode);
            processTaskList.add(processTask);

        }
        processTaskRepository.saveAll(processTaskList);
        pcbTask.setPcb_task_status("已下达已投产");
        pcbTaskRepository.save(pcbTask);
        return  ResultVoUtil.success("下达切分完成");
    }

    @Override
    @Transactional
    public ResultVo putIntoProduce(PcbTaskReq pcbTaskReq) {

        ProcessTask processTask = null;
        Optional<ProcessTask> optional = processTaskRepository.findById(pcbTaskReq.getProcessTaskId());;
        processTask = optional.isPresent()?optional.get():null;
        if(processTask==null){
            return ResultVoUtil.error("查找不到该工序计划");
        }
        processTask.setDevice_code(pcbTaskReq.getDeviceCode());
        processTask.setDevice_name(pcbTaskReq.getDeviceName());
        processTask.setPlan_start_time(pcbTaskReq.getPlanStartTime());
        processTask.setPlan_finish_name(pcbTaskReq.getPlanFinishTime());
        processTask.setAmount_completed(pcbTaskReq.getAmountCompleted());
        processTask.setProcess_task_status("已下达到机台");
        processTaskRepository.save(processTask);
        String[] split = pcbTaskReq.getDeviceCode().split(",");
        List<ProcessTaskDevice> list = new ArrayList<>();
        for (int i=0;i<split.length;i++){
            String deviceCode = split[i];
            ProcessTaskDevice processTaskDevice = new ProcessTaskDevice();
            processTaskDevice.setAmount(0);
            processTaskDevice.setDevice_code(deviceCode);
            processTaskDevice.setProcess_task_code(processTask.getProcess_task_code());
            processTaskDevice.setPlant_code("");
            processTaskDevice.setTd_status("");
            list.add(processTaskDevice);
        }
        processTaskDeviceRepository.saveAll(list);
        return ResultVoUtil.success("工序计划下达到机台成功");
    }

    @Override
    public ResultVo findProcessTaskByPCBTaskId(Long id) {

        final List<ProcessTask> processTaskList = processTaskRepository.findAllByPcb_task_id(id);

        return ResultVoUtil.success(processTaskList);
    }

    @Override
    public ResultVo findProcessTaskByProcessName(PcbTaskReq pcbTaskReq) {

        StringBuffer sql = new StringBuffer("select * from(\n" +
                "select *, ROW_NUMBER() OVER(order by t4.Id asc) row from\n" +
                "(SELECT t1.*,t2.pcb_id,t2.feeding_task_code from produce_process_task t1 LEFT JOIN produce_pcb_task t2 on t2.pcb_task_code = t1.pcb_task_code where process_name = '备料')t4)t3\n");
        Integer page = 1;
        Integer size = 10;
        if(pcbTaskReq.getPage()==null||pcbTaskReq.getSize()==null){
            page = pcbTaskReq.getPage();
            size = pcbTaskReq.getSize();
        }

        List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());

        sql.append("where t3.Row between " +
                ((page-1)*size+1) +
                " and " +
                (page*size) +
                "");


        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        return ResultVoUtil.success("查询成功",mapList,count.size());
    }

    @Override
    public ResultVo findFeedingTask(PcbTaskReq pcbTaskReq) {
        List<FeedingTask> list = feedingTaskRepository.findByFeeding_task_code(pcbTaskReq.getFeedindTaskCode());

        return ResultVoUtil.success(list);
    }
}