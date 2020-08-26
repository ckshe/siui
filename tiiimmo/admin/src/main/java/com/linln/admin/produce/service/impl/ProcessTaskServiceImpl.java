package com.linln.admin.produce.service.impl;

import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.admin.base.domain.*;
import com.linln.admin.base.domain.Process;
import com.linln.admin.base.repository.*;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.ProcessTaskDetail;
import com.linln.admin.produce.domain.ProcessTaskDetailDevice;
import com.linln.admin.produce.domain.ProcessTaskStatusHistory;
import com.linln.admin.produce.repository.ProcessTaskDetailDeviceRepository;
import com.linln.admin.produce.repository.ProcessTaskDetailRepositoty;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.produce.repository.ProcessTaskStatusHistoryRepository;
import com.linln.admin.produce.service.ProcessTaskService;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.constant.CommonConstant;
import com.linln.utill.DateUtil;
import com.linln.utill.FileUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProcessTaskServiceImpl implements ProcessTaskService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ProcessTaskDetailRepositoty processTaskDetailRepositoty;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Autowired
    private OperationInstructionRepository operationInstructionRepository;

    @Autowired
    private OperationManualRepository operationManualRepository;

    @Autowired
    private ProcessTaskStatusHistoryRepository processTaskStatusHistoryRepository;

    @Autowired
    private TaskInstructionRepository taskInstructionRepository;

    @Autowired
    private ProcessTaskDetailDeviceRepository processTaskDetailDeviceRepository;

    @Autowired
    private ESOPRepository esopRepository;
    @Override
    public ResultVo findProcessTask(ProcessTaskReq processTaskReq) {

        Integer page = processTaskReq.getPage(); //当前页
        Integer size = processTaskReq.getSize(); //每页条数
        String pcbTaskCode = processTaskReq.getPcbTaskCode();  //任务单号
        String taskSheetCode = processTaskReq.getTaskSheetCode(); //生产批次
        String pcbCode = processTaskReq.getPcbCode(); //规格型号
        String pcbName = processTaskReq.getPcbName(); //物料名称
        Date planStartTimeBegin = processTaskReq.getPlanStartTimeBegin(); //计划开始时间
        Date planStartTimeOver = processTaskReq.getPlanStartTimeOver();
        Date planFinishTimeBegin = processTaskReq.getPlanFinishTimeBegin(); //计划完成时间
        Date planFinishTimeOver = processTaskReq.getPlanFinishTimeOver();
        Date startTimeBegin = processTaskReq.getStartTimeBegin(); //实际生产时间
        Date startTimeOver = processTaskReq.getStartTimeOver();
        Date finishTimeBegin = processTaskReq.getFinishTimeBegin(); //实际完成时间
        Date finishTimeOver = processTaskReq.getFinishTimeOver();

        if(page == null||size == null){
            page = 1;
            size = 10;
        }

        StringBuffer wheresql = new StringBuffer(" where 1=1 ");
        if(pcbTaskCode!=null&&!"".equals(pcbTaskCode)){
            wheresql.append(" and pcb_task_code  like '" +
                    "%" + pcbTaskCode + "%" +
                    "' ");
        }

        if(taskSheetCode!=null&&!"".equals(taskSheetCode)){
            wheresql.append(" and task_sheet_code  like '" +
                    "%" + taskSheetCode + "%" +
                    "' ");
        }

        if(pcbCode!=null&&!"".equals(pcbCode)){
            wheresql.append(" and pcb_code  like '" +
                    "%" + pcbCode + "%" +
                    "' ");
        }

        if(pcbName!=null&&!"".equals(pcbName)){
            wheresql.append(" and pcb_name  like '" +
                    "%" + pcbName + "%" +
                    "' ");
        }

        if(planStartTimeBegin!=null&&!"".equals(planStartTimeBegin)&&planStartTimeOver!=null&&!"".equals(planStartTimeOver)){
            String planStartTimeBeginString = DateUtil.date2String(planStartTimeBegin, "yyyy-MM-dd") + " 00:00:00";
            String planStartTimeOverString = DateUtil.date2String(planStartTimeOver, "yyyy-MM-dd") + " 23:59:59";

            wheresql.append(" and plan_start_time >= '" +
                    planStartTimeBeginString +
                    "' ");
            wheresql.append(" and plan_start_time <= '" +
                    planStartTimeOverString  +
                    "' ");
        }
        if(planFinishTimeBegin!=null&&!"".equals(planFinishTimeBegin)&&planFinishTimeOver!=null&&!"".equals(planFinishTimeOver)){
            String planFinishTimeBeginString = DateUtil.date2String(planFinishTimeBegin, "yyyy-MM-dd") + " 00:00:00";
            String planFinishTimeOverString = DateUtil.date2String(planFinishTimeOver, "yyyy-MM-dd") + " 23:59:59";

            wheresql.append(" and plan_finish_time >= '" +
                    planFinishTimeBeginString +
                    "' ");
            wheresql.append(" and plan_finish_time <= '" +
                    planFinishTimeOverString  +
                    "' ");
        }
        if(startTimeBegin!=null&&!"".equals(startTimeBegin)&&startTimeOver!=null&&!"".equals(startTimeOver)){
            String startTimeBeginString = DateUtil.date2String(startTimeBegin, "yyyy-MM-dd") + " 00:00:00";
            String startTimeOverString = DateUtil.date2String(startTimeOver, "yyyy-MM-dd") + " 23:59:59";

            wheresql.append(" and start_time >= '" +
                    startTimeBeginString +
                    "' ");
            wheresql.append(" and start_time <= '" +
                    startTimeOverString  +
                    "' ");
        }
        if(finishTimeBegin!=null&&!"".equals(finishTimeBegin)&&finishTimeOver!=null&&!"".equals(finishTimeOver)){
            String finishTimeBeginString = DateUtil.date2String(finishTimeBegin, "yyyy-MM-dd") + " 00:00:00";
            String finishTimeOverString = DateUtil.date2String(finishTimeOver, "yyyy-MM-dd") + " 23:59:59";

            wheresql.append(" and finish_time >= '" +
                    finishTimeBeginString +
                    "' ");
            wheresql.append(" and finish_time <= '" +
                    finishTimeOverString  +
                    "' ");
        }
        //System.out.println(wheresql);

        StringBuffer sql = new StringBuffer("select  *\n" +
                "                from (select row_number()\n" +
                "                over(order by plan_start_time desc) as rownumber,*\n" +
                "                from produce_process_task " +
                wheresql.toString() +
                ") temp_row ");

        List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());
        sql.append(" where rownumber between " +
                ((page-1)*size+1) +
                " and " +
                (page*size) +
                "");

        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());

        return ResultVoUtil.success("查询成功",mapList,count.size());
    }


    @Override
    @Transactional
    public void addTaskDetailList(ProcessTaskReq req) {
        String processTaskCode = req.getProcess_task_code();
        List<ProcessTaskDetail> details = req.getDetailList();
        ProcessTask processTask = processTaskRepository.findByProcessTaskCode(processTaskCode);
        Process process = processRepository.findByProcessName(processTask.getProcess_name());
        processTaskDetailRepositoty.deleteByByProcess_task_code(processTaskCode);
        processTaskDetailDeviceRepository.deleteByTaskCode(processTaskCode);
        for(ProcessTaskDetail detail : details){
            if(process.getCount_type()!=0&&detail.getDetailDeviceList()!=null&&detail.getDetailDeviceList().size()!=0){
                //非贴片任务
                for(ProcessTaskDetailDevice detailDevice : detail.getDetailDeviceList()){
                    if(detailDevice.getPlan_count()==null){
                        detailDevice.setPlan_count(0);
                    }
                    if(detailDevice.getFinish_count()==null){
                        detailDevice.setFinish_count(0);
                    }
                    detailDevice.setProcess_task_code(processTaskCode);
                    detailDevice.setPlan_day_time(detail.getPlan_day_time());
                    if(detailDevice.getDevice_detail_status()!=null&&!detailDevice.equals("")){

                    }else {
                        detailDevice.setDevice_detail_status("已下达");
                    }
                }
                processTaskDetailDeviceRepository.saveAll(detail.getDetailDeviceList());
            }
            detail.setDetail_type("人创建");
            detail.setStatus(StatusEnum.OK.getCode());
            if(detail.getFinish_count()==null){
                detail.setFinish_count(0);
            }
            if(detail.getPlan_count()==null){
                detail.setPlan_count(0);
            }
            detail.setProcess_name(processTask.getProcess_name());
        }

        Integer finishCount = details.stream().mapToInt(ProcessTaskDetail::getFinish_count).sum();
        if(finishCount>=processTask.getPcb_quantity()){
            Date today =  new Date();
            processTask.setProcess_task_status("已完成");
            processTask.setFinish_time(today);
            //新增一条操作历史记录
            List<ProcessTaskStatusHistory> historyList = processTaskStatusHistoryRepository.findByProcessTaskCodeLastRecordList(processTask.getProcess_task_code());
            //step3:状态不同结束上一条并计算持续时间，新增一条
            for(ProcessTaskStatusHistory history : historyList){
                history.setEnd_time(today);
                Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                history.setContinue_time(Integer.parseInt(cha+""));
                processTaskStatusHistoryRepository.save(history);
            }

            //新增
            ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
            newRecord.setStart_time(today);
            newRecord.setContinue_time(0);
            newRecord.setDevice_code(processTask.getDevice_code());
            newRecord.setProcess_task_status("已完成");
            newRecord.setDevice_name(processTask.getDevice_name());
            newRecord.setProcess_task_code(processTask.getProcess_task_code());
            newRecord.setProcess_name(processTask.getProcess_name());
            newRecord.setEnd_time(today);
            processTaskStatusHistoryRepository.save(newRecord);
        }else if(finishCount<processTask.getPcb_quantity()&&processTask.getProcess_task_status().equals("已完成")){
            Date today =  new Date();
            processTask.setProcess_task_status("暂停");
            processTask.setFinish_time(today);
            //新增一条操作历史记录
            List<ProcessTaskStatusHistory> historyList = processTaskStatusHistoryRepository.findByProcessTaskCodeLastRecordList(processTask.getProcess_task_code());
            //step3:状态不同结束上一条并计算持续时间，新增一条
            for(ProcessTaskStatusHistory history : historyList){
                history.setEnd_time(today);
                Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                history.setContinue_time(Integer.parseInt(cha+""));
                processTaskStatusHistoryRepository.save(history);
            }

            //新增
            ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
            newRecord.setStart_time(today);
            newRecord.setContinue_time(0);
            newRecord.setDevice_code(processTask.getDevice_code());
            newRecord.setProcess_task_status("暂停");
            newRecord.setDevice_name(processTask.getDevice_name());
            newRecord.setProcess_task_code(processTask.getProcess_task_code());
            newRecord.setProcess_name(processTask.getProcess_name());
            newRecord.setEnd_time(today);
            processTaskStatusHistoryRepository.save(newRecord);
        }
        processTask.setAmount_completed(finishCount);
        processTaskRepository.save(processTask);
        processTaskDetailRepositoty.saveAll(details);
    }

    @Override
    public List<ProcessTaskDetail> findProcessTaskDeatilList(String processTaskCode) {

        List<ProcessTaskDetail> detailList = processTaskDetailRepositoty.findByProcess_task_code(processTaskCode);
        for(ProcessTaskDetail detail : detailList){
            String daystr = DateUtil.date2String(detail.getPlan_day_time(),"");
            List<ProcessTaskDetailDevice> detailDeviceList = processTaskDetailDeviceRepository.findByTaskCodeAndDayTime(detail.getProcess_task_code(), daystr);

            detail.setDetailDeviceList(detailDeviceList);
        }
        return detailList;
    }

    @Override
    public void deleteProcessTaskDetailById(Long id) {
        processTaskDetailRepositoty.deleteById(id);
    }

    @Override
    public List<ProcessTaskDetailDevice> findByTaskCodeAndDayTime(String processTaskCode, String planDayTime) {

        return processTaskDetailDeviceRepository.findByTaskCodeAndDayTime(processTaskCode,planDayTime);
    }

    @Override
    public List<ProcessTaskDetailDevice> findByTaskCodeAndDayTimeAndDeviceCode(String processTaskCode, String planDayTime, String deviceCode) {
        return processTaskDetailDeviceRepository.findByTaskCodeAndDayTimeAndDeviceCode(processTaskCode,planDayTime,deviceCode);
    }

    @Override
    public void showPDF(HttpServletResponse response, String deviceCode,String type) {
        Device device= deviceRepository.findbyDeviceCode(deviceCode);
        String path = "";
        //操作指导书
        if("1".equals(type)){
            OperationInstruction operationInstruction = operationInstructionRepository.findByCode(device.getUse_book());
            if(operationInstruction ==null){
                return;
            }
            path = CommonConstant.file_path+CommonConstant.usebook_path+operationInstruction.getUploadFile();
        }else {
            //作业指导书
            TaskInstruction taskInstruction = taskInstructionRepository.findByCode(device.getWork_book());
            if(taskInstruction ==null){
                return;
            }
            path = CommonConstant.file_path+CommonConstant.workbook_path+taskInstruction.getUploadFile();

        }
        FileUtil.readPDF(response,path);
    }

    @Override
    public void showESOPPDF(HttpServletResponse response, String pcbCode) {
        ESOP esop = esopRepository.findByPcbCode(pcbCode);
        String path = "";
        if(esop ==null){
            return;
        }
        path = CommonConstant.file_path+CommonConstant.esop_path+esop.getUploadFile();

        FileUtil.readPDF(response,path);
    }

    @Override
    public ResultVo getProcessTaskDetailByPage(ProcessTaskReq processTaskReq) {

        Integer page = processTaskReq.getPage(); //当前页
        Integer size = processTaskReq.getSize(); //每页条数
        String taskSheetCode = processTaskReq.getTaskSheetCode(); //生产批次
        String pcbTaskCode = processTaskReq.getPcbTaskCode();  //任务单号
        String pcbCode = processTaskReq.getPcbCode(); //规格型号
        String status = processTaskReq.getStatus();//状态

        Date startTime = processTaskReq.getStartTime(); //实际生产时间

        Date endTime = processTaskReq.getFinishTime();//实际结束时间


        if(page == null||size == null){
            page = 1;
            size = 10;
        }

        StringBuffer wheresql = new StringBuffer(" where 1=1 ");
        if(pcbTaskCode!=null&&!"".equals(pcbTaskCode)){
            wheresql.append(" and t2.pcb_task_code  like '" +
                    "%" + pcbTaskCode + "%" +
                    "' ");
        }

        if(taskSheetCode!=null&&!"".equals(taskSheetCode)){
            wheresql.append(" and t2.task_sheet_code  like '" +
                    "%" + taskSheetCode + "%" +
                    "' ");
        }

        if(pcbCode!=null&&!"".equals(pcbCode)){
            wheresql.append(" and t2. pcb_code  like '" +
                    "%" + pcbCode + "%" +
                    "' ");
        }
        if(status!=null&&!"".equals(status)){
            if("已完成".equals(status)){
                wheresql.append(" and t1.finish_count>= t1.plan_count ");
            }else {
                wheresql.append(" and t1.finish_count< t1.plan_count ");
            }

        }
        if(startTime!=null&&!"".equals(startTime)&&endTime!=null&&!"".equals(endTime)){
            String startTimeString = DateUtil.date2String(startTime,"yyyy-MM-dd") + " 00:00:00";
            String endTimeString = DateUtil.date2String(endTime,"yyyy-MM-dd") + " 23:59:59";
            //startTimeString.substring(0,10);
            wheresql.append(" AND t1.plan_day_time >= '" +
                    startTimeString  +
                    "' AND t1.plan_day_time <= '" +
                    endTimeString +
                    "'");

        }


        //System.out.println(wheresql);

        StringBuffer sql = new StringBuffer("SELECT *\n" +
                "FROM\n" +
                "\t( SELECT row_number ( ) OVER ( ORDER BY plan_day_time DESC ) AS rownumber,t1.*,(CASE \n" +
                "\tWHEN t1.finish_count>= t1.plan_count  THEN '已完成'\n" +
                "\tELSE '进行中'\n" +
                "END) as detail_status,\n" +
                " t2.pcb_code,t2.pcb_task_code,t2.task_sheet_code FROM produce_process_task_detail t1  LEFT JOIN produce_process_task t2 on t2.process_task_code = t1.process_task_code " +
                wheresql +
                ") temp_row  ");

        List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());
        sql.append(" where rownumber between " +
                ((page-1)*size+1) +
                " and " +
                (page*size) +
                "");

        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());

        return ResultVoUtil.success("查询成功",mapList,count.size());


    }
}

