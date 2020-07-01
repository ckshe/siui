package com.linln.admin.produce.service.impl;

import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.admin.produce.service.ProcessTaskService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProcessTaskServiceImpl implements ProcessTaskService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ResultVo findProcessTask(ProcessTaskReq processTaskReq) {

        Integer page = processTaskReq.getPage(); //当前页
        Integer size = processTaskReq.getSize(); //每页条数
        String pcbTaskCode = processTaskReq.getPcbTaskCode();  //任务单号
        String taskSheetCode = processTaskReq.getTaskSheetCode(); //生产批次
        String pcbCode = processTaskReq.getPcbCode(); //规格型号
        String pcbName = processTaskReq.getPcbName(); //物料名称
        Date planStartTime = processTaskReq.getPlanStartTime(); //计划开始时间
        Date planFinishTime = processTaskReq.getPlanFinishTime(); //计划完成时间
        Date startTime = processTaskReq.getStartTime(); //实际生产时间
        Date finishTime = processTaskReq.getFinishTime(); //实际完成时间

        if(page == null||size == null){
            page = processTaskReq.getPage();
            size = processTaskReq.getSize();
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

        if(planStartTime!=null&&!"".equals(planStartTime)){
            String plantStartTimeString = DateUtil.date2String(planStartTime, "yyyy-MM-dd HH:mm:ss");
            wheresql.append(" and plan_start_time  = '" +
                    plantStartTimeString +
                    "' ");
        }
        if(planFinishTime!=null&&!"".equals(planFinishTime)){
            String planFinishTimeString = DateUtil.date2String(planFinishTime, "yyyy-MM-dd HH:mm:ss");
            wheresql.append(" and plan_finish_time  = '" +
                    planFinishTimeString +
                    "' ");
        }
        if(startTime!=null&&!"".equals(startTime)){
            String startTimeString = DateUtil.date2String(startTime,"yyyy-MM-dd").substring(0,10);
            //startTimeString.substring(0,10);
            wheresql.append(" and start_time >= '" +
                    startTimeString + " 00:00:00" +
                    "' ");
            wheresql.append(" and start_time <= '" +
                    startTimeString + " 23:59:59" +
                    "' ");
        }

        if(finishTime!=null&&!"".equals(finishTime)){
            String finishTimeString = DateUtil.date2String(finishTime,"yyyy-MM-dd").substring(0,10);
            wheresql.append(" and finish_time >= '" +
                    finishTimeString + " 00:00:00" +
                    "' ");
            wheresql.append(" and finish_time <= '" +
                    finishTimeString + " 23:59:59" +
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
}

