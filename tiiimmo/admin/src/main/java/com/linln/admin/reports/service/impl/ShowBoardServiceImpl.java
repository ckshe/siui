package com.linln.admin.reports.service.impl;

import com.linln.RespAndReqs.responce.ProcessThisWeekRateResp;
import com.linln.RespAndReqs.responce.StaffOntimeRateResp;
import com.linln.admin.base.domain.Device;
import com.linln.admin.base.repository.DeviceRepository;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.UserDeviceHistory;
import com.linln.admin.produce.repository.PcbTaskRepository;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.reports.service.ShowBoardService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShowBoardServiceImpl implements ShowBoardService {


    @Autowired
    private PcbTaskRepository pcbTaskRepository;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<PcbTask> pcbTaskBoard() {

        Map<String,String> thisWeekDate = DateUtil.getThisWeek(new Date());
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";
        List<PcbTask> allByStartEndTime = pcbTaskRepository.findAllByStartEndTime(startTime, endTime);
        return allByStartEndTime;

    }

    @Override
    public Map<String,Object> getMapWeekRate() {
        Map<String,Object> map = new HashMap<>();
        //本周即第四周
        Map<String,String> thisWeekDate4 = DateUtil.getThisWeek(new Date());
        String startTime4 = thisWeekDate4.get("weekBegin")+" 00:00:00";
        String endTime4 = thisWeekDate4.get("weekEnd")+" 23:59:59";
        List<PcbTask> pcbTask4 = pcbTaskRepository.findAllByStartEndTime(startTime4,endTime4);
        int week4Finish = (int) pcbTask4.stream().filter(p -> "完成".equals(p.getPcb_task_status())).count();
        int week4All = pcbTask4.size()==0?1:pcbTask4.size();
        BigDecimal rate4 = caculateRate(week4Finish, week4All);


        //第三周
        Date week3 = DateUtil.dateAddNum(new Date(),-7);
        Map<String,String> thisWeekDate3 = DateUtil.getThisWeek(week3);
        String startTime3 = thisWeekDate3.get("weekBegin")+" 00:00:00";
        String endTime3 = thisWeekDate3.get("weekEnd")+" 23:59:59";
        List<PcbTask> pcbTask3 = pcbTaskRepository.findAllByStartEndTime(startTime3,endTime3);
        int week3Finish = (int) pcbTask3.stream().filter(p -> "完成".equals(p.getPcb_task_status())).count();
        int week3All = pcbTask3.size()==0?1:pcbTask3.size();
        BigDecimal rate3 = caculateRate(week3Finish, week3All);


        //第二周
        Date week2 = DateUtil.dateAddNum(new Date(),-14);
        Map<String,String> thisWeekDate2 = DateUtil.getThisWeek(week2);
        String startTime2 = thisWeekDate2.get("weekBegin")+" 00:00:00";
        String endTime2 = thisWeekDate2.get("weekEnd")+" 22:59:59";
        List<PcbTask> pcbTask2 = pcbTaskRepository.findAllByStartEndTime(startTime2,endTime2);

        int week2Finish = (int) pcbTask2.stream().filter(p -> "完成".equals(p.getPcb_task_status())).count();
        int week2All = pcbTask2.size()==0?1:pcbTask2.size();
        BigDecimal rate2 = caculateRate(week2Finish, week2All);


        //第一周
        Date week1 = DateUtil.dateAddNum(new Date(),-21);
        Map<String,String> thisWeekDate1 = DateUtil.getThisWeek(week1);
        String startTime1 = thisWeekDate1.get("weekBegin")+" 00:00:00";
        String endTime1 = thisWeekDate1.get("weekEnd")+" 11:59:59";
        List<PcbTask> pcbTask1 = pcbTaskRepository.findAllByStartEndTime(startTime1,endTime1);
        int week1Finish = (int) pcbTask1.stream().filter(p -> "完成".equals(p.getPcb_task_status())).count();
        int week1All = pcbTask1.size()==0?1:pcbTask1.size();
        BigDecimal rate1 = caculateRate(week1Finish, week1All);

        map.put("week1",rate1);
        map.put("week2",rate2);
        map.put("week3",rate3);
        map.put("week4",rate4);
        return map;
    }

    @Override
    public Map<String,Object> getMapProcessWeekRate() {
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(new Date());
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";
        List<ProcessTask> processTaskList = processTaskRepository.findByStartEndTime(startTime, endTime);
        //贴片工序任务
        List<ProcessTask> processTaskListTiepian = processTaskList.stream().filter(p -> "贴片A".equals(p.getProcess_name())||"贴片B".equals(p.getProcess_name())||"备料".equals(p.getProcess_name())||"贴片质检".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish1Count = (int)processTaskListTiepian.stream().filter(processTask -> "完成".equals(processTask.getProcess_task_status())).count();
        int all1Count = processTaskListTiepian.size()==0?1:processTaskListTiepian.size();
        BigDecimal rate1 = caculateRate(finish1Count, all1Count);

        //后焊工序任务
        List<ProcessTask> processTaskListhouhan = processTaskList.stream().filter(p -> "手插质检".equals(p.getProcess_name())||"手插".equals(p.getProcess_name())||"波峰焊".equals(p.getProcess_name())||"自动焊".equals(p.getProcess_name())||"人工焊".equals(p.getProcess_name())||"后焊终检".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish2Count = (int)processTaskListhouhan.stream().filter(processTask -> "完成".equals(processTask.getProcess_task_status())).count();
        int all2Count = processTaskListhouhan.size()==0?1:processTaskListhouhan.size();
        BigDecimal rate2 = caculateRate(finish2Count, all2Count);

        //调试工序任务
        List<ProcessTask> processTaskListtiaoshi = processTaskList.stream().filter(p -> "单板调试".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish3Count = (int)processTaskListtiaoshi.stream().filter(processTask -> "完成".equals(processTask.getProcess_task_status())).count();
        int all3Count = processTaskListtiaoshi.size()==0?1:processTaskListtiaoshi.size();
        BigDecimal rate3 = caculateRate(finish3Count, all3Count);

        Map<String,Object> map = new HashMap<>();

        map.put("tiepian",rate1);
        map.put("houhan",rate2);
        map.put("tiaoshi",rate1);

        return map;
    }

    private BigDecimal caculateRate(int finishCount, int allcount) {
        BigDecimal finish2 = new BigDecimal(finishCount);
        BigDecimal all2 = new BigDecimal(allcount==0?1:allcount);
        return finish2.divide(all2).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
    }

    @Override
    public List<Map<String, Object>> getTaskFinishRate() {
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(new Date());
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";
        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\t( CASE SUM ( ISNULL( pcb_quantity, 0 ) ) WHEN 0 THEN 1 ELSE SUM ( ISNULL( pcb_quantity, 0 ) ) END ) AS plancount,\n" +
                "\tSUM ( ISNULL( amount_completed, 0 ) ) AS finishcount,\n" +
                "\ttask_sheet_code,\n" +
                "\t cast(CAST((SUM ( ISNULL( amount_completed, 0 ) ))*1.0 / ( CASE SUM ( ISNULL( pcb_quantity, 0 ) ) WHEN 0 THEN 1 ELSE SUM ( ISNULL( pcb_quantity, 0 ) ) END ) as decimal(8,2)) AS varchar(100)) AS rate  \n" +
                "FROM\n" +
                "\tproduce_pcb_task WHERE produce_plan_date >= '" +
                startTime +
                "'\n and produce_plan_complete_date<= '" +
                endTime +
                "'" +
                " GROUP BY\n" +
                "\ttask_sheet_code");
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        return mapList;
    }


    @Override
    public List<Device> getDeviceStatus() {
        return deviceRepository.findAllbyDeviceSort();
    }

    @Override
    public  List<Map<String,Object>> staffOnBoard() {

        String today = DateUtil.date2String(new Date(),"");
        StringBuffer sql = new StringBuffer("\n" +
                "SELECT\n" +
                "\tt1.device_code,\n" +
                "\tt1.process_task_code,\n" +
                "\tt1.user_id,\n" +
                "\tt1.user_name,\n" +
                "\tt1.up_time,\n" +
                "\tt1.down_time,\n" +
                "\tISNULL(t2.amount_completed, 0) finishcount,\n" +
                "\tt2.process_name,\n" +
                "\tISNULL(t2.pcb_quantity, 0) plancount,\n" +
                "\t\tcast(100*CAST(ISNULL(t2.amount_completed, 0)*1.0/ISNULL(t2.pcb_quantity, 1) as decimal(8,2)) AS varchar(100))  AS rate\n\n" +
                "FROM\n" +
                "\tproduce_user_device_history t1\n" +
                "\tLEFT JOIN produce_process_task t2 ON t2.process_task_code = t1.process_task_code\n" +
                "\tWHERE t1.process_task_code != '未分配' and t1.process_task_code is not null AND  CONVERT(varchar(100), t1.up_time, 23) = '" +
                today +
                "'");

        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        return mapList;
    }

    @Override
    public ProcessTask findByProcessTaskCode(String processTaskCode) {
        return processTaskRepository.findByProcessTaskCode(processTaskCode);
    }

    @Override
    public List<ProcessTask> findProcessTaskByDate() {
        String today = DateUtil.date2String(new Date(),"");
        String startTime = today +" 00:00:00";
        String endTime = today +" 23:59:59";
        List<ProcessTask> processTaskList = processTaskRepository.findByStartEndTime(startTime, endTime);
        return processTaskList;
    }

    @Override
    public List<ProcessThisWeekRateResp> getMapProcessThisWeekRate() {

        Map<String,String> thisWeekDate = DateUtil.getThisWeek(new Date());
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";

        List<String> dayList = DateUtil.getThisWeekDayListUtillToday(new Date());
        StringBuffer allsql = new StringBuffer("SELECT\n" +
                "\tCOUNT(id) allcount,\n" +
                "\tCONVERT ( VARCHAR ( 100 ), plan_finish_time, 23 ) theday\n" +
                "FROM\n" +
                "\tproduce_process_task \n" +
                "WHERE\n" +
                "\tplan_finish_time > = '" +
                startTime +
                "' \n" +
                "\tAND plan_finish_time <= '" +
                endTime  +
                "' \n"+
                "GROUP BY\n" +
                "\tCONVERT ( VARCHAR ( 100 ), plan_finish_time, 23 )");
        StringBuffer finishsql = new StringBuffer("SELECT\n" +
                "\tCOUNT(id) finishCount,\n" +
                "\tCONVERT ( VARCHAR ( 100 ), plan_finish_time, 23 ) theday,SUM(amount_completed) sumfinishcount\n" +
                "FROM\n" +
                "\tproduce_process_task \n" +
                "WHERE\n" +
                "\tplan_finish_time > = '" +
                startTime +
                "' \n" +
                "\tAND plan_finish_time <= '" +
                endTime +

                "' AND process_task_status = '完成'\n" +
                "GROUP BY\n" +
                "\t CONVERT ( VARCHAR ( 100 ), plan_finish_time, 23 )");
        List<Map<String,Object>> allList = jdbcTemplate.queryForList(allsql.toString());
        List<Map<String,Object>> finishList = jdbcTemplate.queryForList(finishsql.toString());
        List<ProcessThisWeekRateResp> result = new ArrayList<>();
        for(String day : dayList){
            ProcessThisWeekRateResp resp = new ProcessThisWeekRateResp();
            resp.setTheDay(day);
            resp.setAllCount(0);
            resp.setFinishCount(0);
            resp.setRate(BigDecimal.ZERO);
            resp.setSumFinishAmount(0);
            result.add(resp);
        }
        for(ProcessThisWeekRateResp resp : result){
            for(Map<String,Object> all : allList){
                String theday = (String) all.get("theday");
                Integer allcount = (Integer) all.get("allcount");
                if(theday.equals(resp.getTheDay())){
                    resp.setAllCount(allcount);
                }
            }
        }
        for(ProcessThisWeekRateResp resp : result){
            for(Map<String,Object> finish : finishList){
                String theday = (String) finish.get("theday");
                Integer finishcount = (Integer) finish.get("finishCount");
                Integer sumFinishAmount = (Integer)finish.get("sumfinishcount");
                if(theday.equals(resp.getTheDay())){
                    resp.setSumFinishAmount(sumFinishAmount);
                    resp.setFinishCount(finishcount);
                }
            }
        }
        for(ProcessThisWeekRateResp resp : result){
            Integer allcount = resp.getAllCount()==0?1:resp.getAllCount();
            Integer finishcount = resp.getFinishCount();
            BigDecimal rate = new BigDecimal(finishcount/allcount).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            resp.setRate(rate);
        }

        return result;
    }


    @Override
    public Map<String, Object> getMapProcessDayRate() {
        String today = DateUtil.date2String(new Date(),"");
        String startTime = today+" 00:00:00";
        String endTime = today+" 23:59:59";
        List<ProcessTask> processTaskList = processTaskRepository.findByStartEndTime(startTime, endTime);
        //贴片工序任务
        List<ProcessTask> processTaskListTiepian = processTaskList.stream().filter(p -> "贴片A".equals(p.getProcess_name())||"贴片B".equals(p.getProcess_name())||"备料".equals(p.getProcess_name())||"贴片质检".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish1Count = (int)processTaskListTiepian.stream().filter(processTask -> "完成".equals(processTask.getProcess_task_status())).count();
        int all1Count = processTaskListTiepian.size()==0?1:processTaskListTiepian.size();
        BigDecimal rate1 = caculateRate(finish1Count, all1Count);

        //后焊工序任务
        List<ProcessTask> processTaskListhouhan = processTaskList.stream().filter(p -> "手插质检".equals(p.getProcess_name())||"手插".equals(p.getProcess_name())||"波峰焊".equals(p.getProcess_name())||"自动焊".equals(p.getProcess_name())||"人工焊".equals(p.getProcess_name())||"后焊终检".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish2Count = (int)processTaskListhouhan.stream().filter(processTask -> "完成".equals(processTask.getProcess_task_status())).count();
        int all2Count = processTaskListhouhan.size()==0?1:processTaskListhouhan.size();
        BigDecimal rate2 = caculateRate(finish2Count, all2Count);

        //调试工序任务
        List<ProcessTask> processTaskListtiaoshi = processTaskList.stream().filter(p -> "单板调试".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish3Count = (int)processTaskListtiaoshi.stream().filter(processTask -> "完成".equals(processTask.getProcess_task_status())).count();
        int all3Count = processTaskListtiaoshi.size()==0?1:processTaskListtiaoshi.size();
        BigDecimal rate3 = caculateRate(finish3Count, all3Count);

        Map<String,Object> map = new HashMap<>();

        map.put("tiepian",rate1);
        map.put("houhan",rate2);
        map.put("tiaoshi",rate1);

        return map;
    }



    @Override
    public  Map<String,Object> getMapProcessTypeDayRate() {
        String today = DateUtil.date2String(new Date(),"");
        String startTime = today+" 00:00:00";
        String endTime = today+" 23:59:59";

        StringBuffer sql = new StringBuffer("\n" +
                "SELECT\n" +
                "\tt1.process_task_code,t1.pcb_quantity,t1.amount_completed ,t2.process_type,\n" +
                "\tISNULL(t1.amount_completed, 0)/ISNULL(t1.pcb_quantity, 1)  rate2,\n" +
                "\t\tcast(100*CAST(ISNULL(t1.amount_completed, 0)*1.0/ISNULL(t1.pcb_quantity, 1) as decimal(8,2)) AS varchar(100))  AS rate\n" +
                "FROM \n" +
                "\tproduce_process_task t1\n" +
                "\tLEFT JOIN base_process t2 ON t2.name = t1.process_name \n" +
                "WHERE  t1.process_task_status != '已完成' AND t1.plan_finish_time  >= '" +
                startTime +
                "' AND t1.plan_finish_time  <= '" +
                endTime +
                "'");
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        List<Map<String, Object>> tiepianList = new ArrayList<>();
        List<Map<String, Object>> houhanList = new ArrayList<>();
        List<Map<String, Object>> zhijianList = new ArrayList<>();
        List<Map<String, Object>> rukuList = new ArrayList<>();
        List<Map<String, Object>> tiaoshiList = new ArrayList<>();

        for(Map<String,Object> map:mapList){
            String  process_type = (String)map.get("process_type");
            if("贴片".equals(process_type)){
                tiepianList.add(map);
            }
            if("后焊".equals(process_type)){
                houhanList.add(map);
            }
            if("质检".equals(process_type)){
                zhijianList.add(map);
            }
            if("调试".equals(process_type)){
                tiaoshiList.add(map);
            }
            if("入库".equals(process_type)){
                rukuList.add(map);
            }
        }
        Map<String,Object> result = new HashMap<>();
        result.put("tiepian",tiepianList);
        result.put("houhan",houhanList);
        result.put("zhijian",zhijianList);
        result.put("tiaoshi",tiaoshiList);
        result.put("ruku",rukuList);
        return result;
    }
    //linltheory
    @Override
    public List<StaffOntimeRateResp>  staffTodayOntimeRate() {
        String today = DateUtil.date2String(new Date(),"");
        String startTime = today+" 00:00:00";
        String endTime = today+" 23:59:59";
        StringBuffer staffOntimeSql = new StringBuffer("\n" +
                "SELECT COUNT(t4.userid) countOnTime,t4.process_type FROM(\n" +
                "\tSELECT\n" +
                "\t\tMAX(t1.user_id) userid ,t3.process_type\n" +
                "\tFROM\n" +
                "\t\tproduce_user_device_history t1\n" +
                "\t\tLEFT JOIN produce_process_task t2 ON t2.process_task_code = t1.process_task_code\n" +
                "\t\tLEFT JOIN base_process t3 on t3.name = t2.process_name\n" +
                "\t\tWHERE t1.up_time >= '" +
                startTime +
                "' and t1.up_time <= '" +
                endTime +
                "' \n" +
                "\tGROUP BY t3.process_type ,t1.user_id\n" +
                "\t\n" +
                "\t) t4 GROUP BY t4.process_type");

        StringBuffer countStaffClassSql =  new StringBuffer("SELECT COUNT(id) staffAllCount, process FROM base_production_shift GROUP BY process");

        StringBuffer processTypeSql = new StringBuffer("SELECT process_type FROM base_process GROUP BY process_type");

        List<Map<String, Object>> processTypeSqlList = jdbcTemplate.queryForList(processTypeSql.toString());
        List<Map<String, Object>> countStaffClassSqlList = jdbcTemplate.queryForList(countStaffClassSql.toString());
        List<Map<String, Object>> staffOntimeSqlList = jdbcTemplate.queryForList(staffOntimeSql.toString());
        List<StaffOntimeRateResp> staffOntimeRateRespList = new ArrayList<>();
        for(Map<String, Object> processType:processTypeSqlList ){
            StaffOntimeRateResp staffOntimeRateResp = new StaffOntimeRateResp();
            String processTypeName = (String)processType.get("process_type");
            staffOntimeRateResp.setProcessType(processTypeName);
            staffOntimeRateResp.setProcessTypeStaffOnTimeCount(0);
            staffOntimeRateResp.setProcessTypeStaffAllCount(0);
            staffOntimeRateResp.setRate(BigDecimal.ZERO);
            for(Map<String, Object> staffOnTime :staffOntimeSqlList){
                String processName = (String)staffOnTime.get("process_type");
                if(processName.equals(processTypeName)){
                    Integer countOnTime = (Integer) staffOnTime.get("countOnTime");
                    staffOntimeRateResp.setProcessTypeStaffOnTimeCount(countOnTime);
                }
            }
            for(Map<String, Object> countStaffClass :countStaffClassSqlList){
                String processName = (String)countStaffClass.get("process");
                if(processName.equals(processTypeName)){
                    Integer staffAllCount = (Integer) countStaffClass.get("staffAllCount");
                    staffOntimeRateResp.setProcessTypeStaffAllCount(staffAllCount);
                }
            }
            staffOntimeRateRespList.add(staffOntimeRateResp);
        }
        for(StaffOntimeRateResp resp : staffOntimeRateRespList){
            BigDecimal rate = caculateRate(resp.getProcessTypeStaffOnTimeCount(),resp.getProcessTypeStaffAllCount());
            resp.setRate(rate);
        }
        return staffOntimeRateRespList;
    }
}
