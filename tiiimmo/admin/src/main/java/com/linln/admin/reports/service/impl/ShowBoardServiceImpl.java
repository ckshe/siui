package com.linln.admin.reports.service.impl;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.RespAndReqs.responce.*;
import com.linln.admin.base.domain.BadNews;
import com.linln.admin.base.domain.Device;
import com.linln.admin.base.domain.DeviceCropRate;
import com.linln.admin.base.repository.BadNewsRepository;
import com.linln.admin.base.repository.DeviceCropRateRepository;
import com.linln.admin.base.repository.DeviceRepository;
import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.domain.ProcessTaskDetail;
import com.linln.admin.produce.domain.UserDeviceHistory;
import com.linln.admin.produce.repository.PcbTaskRepository;
import com.linln.admin.produce.repository.ProcessTaskDetailRepositoty;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.produce.repository.UserDeviceHistoryRepository;
import com.linln.admin.reports.service.ShowBoardService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.system.domain.Role;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.UserRepository;
import com.linln.modules.system.service.RoleService;
import com.linln.utill.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDeviceHistoryRepository userDeviceHistoryRepository;

    @Autowired
    private DeviceCropRateRepository deviceCropRateRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BadNewsRepository badNewsRepository;

    @Autowired
    private ProcessTaskDetailRepositoty processTaskDetailRepositoty;

    @Override
    public List<PcbTask> pcbTaskBoard() {

        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(xdate);
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";
        List<PcbTask> allByStartEndTime = pcbTaskRepository.findAllByStartEndTime(startTime, endTime);
        return allByStartEndTime;

    }

    @Override
    public Map<String,Object> getMapWeekRate() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        Map<String,Object> map = new HashMap<>();
        //本周即第四周
        Map<String,String> thisWeekDate4 = DateUtil.getThisWeek(xdate);
        String startTime4 = thisWeekDate4.get("weekBegin")+" 00:00:00";
        String endTime4 = thisWeekDate4.get("weekEnd")+" 23:59:59";
        List<PcbTask> pcbTask4 = pcbTaskRepository.findAllByStartEndTime(startTime4,endTime4);
        int week4Finish = (int) pcbTask4.stream().filter(p -> "已完成".equals(p.getPcb_task_status())).count();
        int week4All = pcbTask4.size()==0?1:pcbTask4.size();
        BigDecimal rate4 = caculateRate(week4Finish, week4All);


        //第三周
        Date week3 = DateUtil.dateAddNum(xdate,-7);
        Map<String,String> thisWeekDate3 = DateUtil.getThisWeek(week3);
        String startTime3 = thisWeekDate3.get("weekBegin")+" 00:00:00";
        String endTime3 = thisWeekDate3.get("weekEnd")+" 23:59:59";
        List<PcbTask> pcbTask3 = pcbTaskRepository.findAllByStartEndTime(startTime3,endTime3);
        int week3Finish = (int) pcbTask3.stream().filter(p -> "已完成".equals(p.getPcb_task_status())).count();
        int week3All = pcbTask3.size()==0?1:pcbTask3.size();
        BigDecimal rate3 = caculateRate(week3Finish, week3All);


        //第二周
        Date week2 = DateUtil.dateAddNum(xdate,-14);
        Map<String,String> thisWeekDate2 = DateUtil.getThisWeek(week2);
        String startTime2 = thisWeekDate2.get("weekBegin")+" 00:00:00";
        String endTime2 = thisWeekDate2.get("weekEnd")+" 22:59:59";
        List<PcbTask> pcbTask2 = pcbTaskRepository.findAllByStartEndTime(startTime2,endTime2);

        int week2Finish = (int) pcbTask2.stream().filter(p -> "已完成".equals(p.getPcb_task_status())).count();
        int week2All = pcbTask2.size()==0?1:pcbTask2.size();
        BigDecimal rate2 = caculateRate(week2Finish, week2All);


        //第一周
        Date week1 = DateUtil.dateAddNum(xdate,-21);
        Map<String,String> thisWeekDate1 = DateUtil.getThisWeek(week1);
        String startTime1 = thisWeekDate1.get("weekBegin")+" 00:00:00";
        String endTime1 = thisWeekDate1.get("weekEnd")+" 11:59:59";
        List<PcbTask> pcbTask1 = pcbTaskRepository.findAllByStartEndTime(startTime1,endTime1);
        int week1Finish = (int) pcbTask1.stream().filter(p -> "已完成".equals(p.getPcb_task_status())).count();
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
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(xdate);
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";
        List<ProcessTaskDetail> details = processTaskDetailRepositoty.findBetweenTime(startTime,endTime);
        //List<ProcessTask> processTaskList = processTaskRepository.findByStartEndTime(startTime, endTime);
        //贴片工序任务

        //List<ProcessTask> processTaskListTiepian = processTaskList.stream().filter(p -> "贴片A".equals(p.getProcess_name())||"贴片B".equals(p.getProcess_name())||"备料".equals(p.getProcess_name())||"贴片质检".equals(p.getProcess_name())).collect(Collectors.toList());
        List<ProcessTaskDetail> details1TiePian = details.stream().filter(d-> "贴片A".equals(d.getProcess_name())||"贴片B".equals(d.getProcess_name())||"备料".equals(d.getProcess_name())||"贴片质检".equals(d.getProcess_name())).collect(Collectors.toList());
        int finish1Count = (int)details1TiePian.stream().filter(d -> d.getFinish_count()>=d.getPlan_count()).count();
        int all1Count = details1TiePian.size()==0?1:details1TiePian.size();
        BigDecimal rate1 = caculateRate(finish1Count, all1Count);

        //后焊工序任务

        List<ProcessTaskDetail> detailsHouhan = details.stream().filter(d-> "手插".equals(d.getProcess_name())||"波峰焊".equals(d.getProcess_name())||"自动焊".equals(d.getProcess_name())||"人工焊".equals(d.getProcess_name())||"后焊线".equals(d.getProcess_name())||"后焊质检".equals(d.getProcess_name())||"手插质检".equals(d.getProcess_name())).collect(Collectors.toList());
        int finish2Count = (int)detailsHouhan.stream().filter(d -> d.getFinish_count()>=d.getPlan_count()).count();
        int all2Count = detailsHouhan.size()==0?1:detailsHouhan.size();
       /* List<ProcessTask> processTaskListhouhan = processTaskList.stream().filter(p -> "手插质检".equals(p.getProcess_name())||"手插".equals(p.getProcess_name())||"波峰焊".equals(p.getProcess_name())||"自动焊".equals(p.getProcess_name())||"人工焊".equals(p.getProcess_name())||"后焊终检".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish2Count = (int)processTaskListhouhan.stream().filter(processTask -> "已完成".equals(processTask.getProcess_task_status())).count();
        int all2Count = processTaskListhouhan.size()==0?1:processTaskListhouhan.size();*/
        BigDecimal rate2 = caculateRate(finish2Count, all2Count);

        //调试工序任务

        List<ProcessTaskDetail> detailsTiaoshi = details.stream().filter(d-> "单板调试".equals(d.getProcess_name())||"入库".equals(d.getProcess_name())).collect(Collectors.toList());
        int finish3Count = (int)detailsTiaoshi.stream().filter(d -> d.getFinish_count()>=d.getPlan_count()).count();
        int all3Count = detailsTiaoshi.size()==0?1:detailsTiaoshi.size();

       /* List<ProcessTask> processTaskListtiaoshi = processTaskList.stream().filter(p -> "单板调试".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish3Count = (int)processTaskListtiaoshi.stream().filter(processTask -> "已完成".equals(processTask.getProcess_task_status())).count();
        int all3Count = processTaskListtiaoshi.size()==0?1:processTaskListtiaoshi.size();*/
        BigDecimal rate3 = caculateRate(finish3Count, all3Count);

        Map<String,Object> map = new HashMap<>();

        map.put("tiepian",rate1);
        map.put("houhan",rate2);
        map.put("tiaoshi",rate3);

        return map;
    }

    private BigDecimal caculateRate(int finishCount, int allcount) {
        BigDecimal finish2 = new BigDecimal(allcount==0?0:finishCount);
        BigDecimal all2 = new BigDecimal(allcount==0?1:allcount);
        return finish2.divide(all2,2, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100));
    }

    @Override
    public List<Map<String, Object>> getTaskFinishRate() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(xdate);
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";
        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\t( CASE SUM ( ISNULL( pcb_quantity, 0 ) ) WHEN 0 THEN 1 ELSE SUM ( ISNULL( pcb_quantity, 0 ) ) END ) AS plancount,\n" +
                "\tSUM ( ISNULL( amount_completed, 0 ) ) AS finishcount,\n" +
                "\ttask_sheet_code,\n" +
                "\t CAST((SUM ( ISNULL( amount_completed, 0 ) ))*100.0 / ( CASE SUM ( ISNULL( pcb_quantity, 0 ) ) WHEN 0 THEN 1 ELSE SUM ( ISNULL( pcb_quantity, 0 ) ) END ) as decimal(8,2))  AS rate  \n" +
                "FROM\n" +
                "\tproduce_pcb_task WHERE produce_plan_complete_date >= '" +
                startTime +
                "'\n and produce_plan_complete_date<= '" +
                endTime +
                "'" +
                " GROUP BY\n" +
                "\ttask_sheet_code ORDER BY rate desc ");
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        return mapList;
    }


    @Override
    public List<Device> getDeviceStatus() {
        return deviceRepository.findAllbyDeviceSort();
    }

    @Override
    public  List<Map<String,Object>> staffOnBoard() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        String today = DateUtil.date2String(xdate,"");
        StringBuffer sql = new StringBuffer("\n" +
                "SELECT\n" +
                "\t* \n" +
                "FROM\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\tt1.device_code,\n" +
                "\t\tt1.process_task_code,\n" +
                "\t\tt1.user_id,\n" +
                "\t\tt1.user_name,\n" +
                "\t\tt1.up_time,\n" +
                "\t\tt1.down_time,\n" +
                "\t\tISNULL( t2.amount_completed, 0 ) finishcount,\n" +
                "\t\tt2.process_name,\n" +
                "\t\tISNULL( t2.pcb_quantity, 0 ) plancount,t3.task_sheet_code,\n" +
                "\t\tt3.pcb_name,\n" +
                "\t\tROW_NUMBER ( ) OVER ( partition BY t1.user_name, t1.device_code ORDER BY t1.id DESC ) AS rn,\n" +
                "\t\tCAST (\n" +
                "\t\t\t100 * CAST ( ISNULL( t2.amount_completed, 0 ) * 1.0 / ISNULL( t2.pcb_quantity, 1 ) AS DECIMAL ( 8, 2 ) ) AS VARCHAR ( 100 ) \n" +
                "\t\t) AS rate \n" +
                "\tFROM\n" +
                "\t\tproduce_user_device_history t1\n" +
                "\t\tLEFT JOIN produce_process_task t2 ON t2.process_task_code = t1.process_task_code " +
                "\tLEFT JOIN produce_pcb_task t3 ON t2.pcb_task_code = t3.pcb_task_code\n" +
                "\tWHERE\n" +
                "\t\tt1.process_task_code != '未分配' \n" +
                "\t\tAND t1.process_task_code IS NOT NULL \n" +
                "\t\tAND CONVERT ( VARCHAR ( 100 ), t1.up_time, 23 ) = '" +
                today +
                "' \n" +
                "\t) AS u \n" +
                "WHERE\n" +
                "\tu.rn = 1");

        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());


        return mapList;
    }

    @Override
    public ProcessTask findByProcessTaskCode(String processTaskCode) {
        return processTaskRepository.findByProcessTaskCode(processTaskCode);
    }

    @Override
    public List<ProcessTask> findProcessTaskByDate() {
        Date now = new Date();
        Date xdate = DateUtil.dateAddHours(now,-1);
        String today = DateUtil.date2String(xdate,"");
        List<ProcessTask> processTaskList = processTaskRepository.findTodayBetweenTime(today);

        return processTaskList;
    }

    @Override
    public List<ProcessThisWeekRateResp> getMapProcessThisWeekRate() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(xdate);
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";

        List<String> dayList = DateUtil.dayBetweenTwoDate(DateUtil.string2Date(startTime, ""),DateUtil.string2Date(endTime, ""));
        List<ProcessTaskDetail> detailList = processTaskDetailRepositoty.findBetweenTime(startTime,endTime);

        List<ProcessThisWeekRateResp> result = new ArrayList<>();
        for(String day : dayList){
            ProcessThisWeekRateResp resp = new ProcessThisWeekRateResp();
            resp.setTheDay(day);
            resp.setAllCount(0);
            resp.setFinishCount(0);
            resp.setRate(BigDecimal.ZERO);
            resp.setSumFinishAmount(0);
            resp.setSumPlanAmount(0);
            result.add(resp);
        }
        for(ProcessThisWeekRateResp resp : result){
            List<ProcessTaskDetail> collect = detailList.stream().filter(d -> DateUtil.date2String(d.getPlan_day_time(), "").equals(resp.getTheDay())).collect(Collectors.toList());
            if(collect!=null&&collect.size()!=0){
                //总计划数量
                int sumPlanCount = collect.stream().mapToInt(ProcessTaskDetail::getPlan_count).sum();
                //总完成数量
                int sumCompleted = collect.stream().mapToInt(ProcessTaskDetail::getFinish_count).sum();
                //总条数
                int allcount = collect.size();
                //总完成的条数
                int finishcount =(int) collect.stream().filter(d -> d.getFinish_count() >= d.getPlan_count()).count();
                resp.setAllCount(allcount);
                resp.setFinishCount(finishcount);
                resp.setSumPlanAmount(sumPlanCount);
                resp.setSumFinishAmount(sumCompleted);
            }
        }
        for(ProcessThisWeekRateResp resp : result){
            Integer allcount = resp.getAllCount()==0?1:resp.getAllCount();
            Integer finishcount = resp.getFinishCount();
            BigDecimal rate = new BigDecimal(finishcount*100/allcount).setScale(4, BigDecimal.ROUND_HALF_UP);
            //BigDecimal rate = new BigDecimal(finishcount/allcount).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            resp.setRate(rate);
        }

        return result;
    }


    @Override
    public Map<String, Object> getMapProcessDayRate() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        String today = DateUtil.date2String(xdate,"");
        String startTime = today+" 00:00:00";
        String endTime = today+" 23:59:59";
        List<ProcessTaskDetail> detailList = processTaskDetailRepositoty.findBetweenTime(startTime,endTime);
       // List<ProcessTask> processTaskList = processTaskRepository.findByStartEndTime(startTime, endTime);
        //贴片工序任务
        List<ProcessTaskDetail> details1TiePian = detailList.stream().filter(d-> "贴片A".equals(d.getProcess_name())||"贴片B".equals(d.getProcess_name())||"备料".equals(d.getProcess_name())||"贴片质检".equals(d.getProcess_name())).collect(Collectors.toList());
        int finish1Count = (int)details1TiePian.stream().filter(d -> d.getFinish_count()>=d.getPlan_count()).count();
        int all1Count = details1TiePian.size()==0?1:details1TiePian.size();
       /* List<ProcessTask> processTaskListTiepian = processTaskList.stream().filter(p -> "贴片A".equals(p.getProcess_name())||"贴片B".equals(p.getProcess_name())||"备料".equals(p.getProcess_name())||"贴片质检".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish1Count = (int)processTaskListTiepian.stream().filter(processTask -> "已完成".equals(processTask.getProcess_task_status())).count();
        int all1Count = processTaskListTiepian.size()==0?1:processTaskListTiepian.size();*/
        BigDecimal rate1 = caculateRate(finish1Count, all1Count);
        //后焊工序任务
        List<ProcessTaskDetail> detailsHouhan = detailList.stream().filter(d-> "手插".equals(d.getProcess_name())||"波峰焊".equals(d.getProcess_name())||"自动焊".equals(d.getProcess_name())||"人工焊".equals(d.getProcess_name())||"后焊线".equals(d.getProcess_name())||"后焊质检".equals(d.getProcess_name())||"手插质检".equals(d.getProcess_name())).collect(Collectors.toList());
        int finish2Count = (int)detailsHouhan.stream().filter(d -> d.getFinish_count()>=d.getPlan_count()).count();
        int all2Count = detailsHouhan.size()==0?1:detailsHouhan.size();
      /*  List<ProcessTask> processTaskListhouhan = processTaskList.stream().filter(p -> "手插质检".equals(p.getProcess_name())||"手插".equals(p.getProcess_name())||"波峰焊".equals(p.getProcess_name())||"自动焊".equals(p.getProcess_name())||"人工焊".equals(p.getProcess_name())||"后焊终检".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish2Count = (int)processTaskListhouhan.stream().filter(processTask -> "已完成".equals(processTask.getProcess_task_status())).count();
        int all2Count = processTaskListhouhan.size()==0?1:processTaskListhouhan.size();*/
        BigDecimal rate2 = caculateRate(finish2Count, all2Count);
        //调试工序任务
        List<ProcessTaskDetail> detailsTiaoshi = detailList.stream().filter(d-> "单板调试".equals(d.getProcess_name())||"入库".equals(d.getProcess_name())).collect(Collectors.toList());
        int finish3Count = (int)detailsTiaoshi.stream().filter(d -> d.getFinish_count()>=d.getPlan_count()).count();
        int all3Count = detailsTiaoshi.size()==0?1:detailsTiaoshi.size();
      /*  List<ProcessTask> processTaskListtiaoshi = processTaskList.stream().filter(p -> "单板调试".equals(p.getProcess_name())).collect(Collectors.toList());
        int finish3Count = (int)processTaskListtiaoshi.stream().filter(processTask -> "已完成".equals(processTask.getProcess_task_status())).count();
        int all3Count = processTaskListtiaoshi.size()==0?1:processTaskListtiaoshi.size();*/
        BigDecimal rate3 = caculateRate(finish3Count, all3Count);

        Map<String,Object> map = new HashMap<>();

        map.put("tiepian",rate1);
        map.put("houhan",rate2);
        map.put("tiaoshi",rate3);

        return map;
    }



    @Override
    public  Map<String,Object> getMapProcessTypeDayRate() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        String today = DateUtil.date2String(xdate,"");
        String startTime = today+" 00:00:00";
        String endTime = today+" 23:59:59";

        StringBuffer sql = new StringBuffer("\n" +
                "SELECT\n" +
                "\tt1.process_task_code,t1.process_name,t1.pcb_quantity,t1.amount_completed ,t2.process_type,\n" +
                "\tISNULL(t1.amount_completed, 0)/ISNULL(t1.pcb_quantity, 1)  rate2,\n" +
                "\t\tcast(100*CAST(ISNULL(t1.amount_completed, 0)*1.0/ISNULL(t1.pcb_quantity, 1) as decimal(8,2)) AS varchar(100))  AS rate\n" +
                "FROM \n" +
                "\tproduce_process_task t1\n" +
                "\tLEFT JOIN base_process t2 ON t2.name = t1.process_name \n" +
                "WHERE   t1.plan_finish_time  >= '" +
                startTime +
                "' AND t1.plan_start_time  <= '" +
                endTime +
                "'");
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        List<Map<String, Object>> tiepianList = new ArrayList<>();
        List<Map<String, Object>> houhanList = new ArrayList<>();
        List<Map<String, Object>> zhijianList = new ArrayList<>();
        List<Map<String, Object>> rukuList = new ArrayList<>();
        List<Map<String, Object>> tiaoshiList = new ArrayList<>();
        List<Map<String, Object>> beiliaoList = new ArrayList<>();


        for(Map<String,Object> map:mapList){
            String  process_type = (String)map.get("process_type");
            String process_name = (String)map.get("process_name");
            if("贴片".equals(process_type)&&!"备料".equals(process_name)){
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
            if("备料".equals(process_name)){
                beiliaoList.add(map);
            }
        }
        Map<String,Object> result = new HashMap<>();
        result.put("tiepian",tiepianList);
        result.put("houhan",houhanList);
        result.put("zhijian",zhijianList);
        result.put("tiaoshi",tiaoshiList);
        result.put("ruku",rukuList);
        result.put("beiliao",beiliaoList);
        return result;
    }

    @Override
    public List<StaffOntimeRateResp>  staffTodayOntimeRate() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        String today = DateUtil.date2String(xdate,"");
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
                "\t\tand t1.process_task_code != '未分配' \n" +        //去掉未分配防止查出process_type为NULL的情况 SSR-0614
                "\tGROUP BY t3.process_type ,t1.user_id\n" +
                "\t\n" +
                "\t) t4 GROUP BY t4.process_type");

        StringBuffer countStaffClassSql =  new StringBuffer("SELECT COUNT(t1.id) staffAllCount, t2.process_type FROM base_production_shift t1 LEFT JOIN base_process t2 ON t2.name = t1.process GROUP BY t2.process_type");

        StringBuffer processTypeSql = new StringBuffer("SELECT process_type FROM base_process GROUP BY process_type");

        StringBuffer processTypeUseRateSql = new StringBuffer("\n" +
                "SELECT SUM\n" +
                "\t((CASE \n" +
                "\tWHEN t1.process_name = '备料' THEN 1\n" +
                "\tELSE t1.amount_completed\n" +
                "END\n" +
                ") * ISNULL( t2.theory_time, 0 ) ) sumTheoryTime,\n" +
                "\tSUM ( ISNULL( t1.work_time, 0 ) ) workTime,\n" +
                "\tt3.process_type \n" +
                "FROM\n" +
                "\tproduce_process_task t1\n" +
                "\tLEFT JOIN produce_process_theory_time t2 ON t1.process_name = t2.process_name \n" +
                "\tAND t1.pcb_code = t2.pcb_code\n" +
                "\tLEFT JOIN base_process t3 ON t1.process_name = t3.name \n" +
                "WHERE\n" +
                "\tt1.plan_finish_time >= '" +
                startTime+
                "' \n" +
                "\tAND t1.plan_finish_time <= '" +
                endTime +
                "' and t1.process_task_status !='已下达' and t1.process_task_status !='未下达'\n" +
                "GROUP BY\n" +
                "\tt3.process_type");

        List<Map<String, Object>> processTypeSqlList = jdbcTemplate.queryForList(processTypeSql.toString());
        List<Map<String, Object>> countStaffClassSqlList = jdbcTemplate.queryForList(countStaffClassSql.toString());
        List<Map<String, Object>> staffOntimeSqlList = jdbcTemplate.queryForList(staffOntimeSql.toString());
        List<Map<String, Object>> processTypeUseRateSqlList = jdbcTemplate.queryForList(processTypeUseRateSql.toString());

        List<StaffOntimeRateResp> staffOntimeRateRespList = new ArrayList<>();
        for(Map<String, Object> processType:processTypeSqlList ){
            StaffOntimeRateResp staffOntimeRateResp = new StaffOntimeRateResp();
            String processTypeName = (String)processType.get("process_type");
            staffOntimeRateResp.setProcessType(processTypeName);
            staffOntimeRateResp.setProcessTypeStaffOnTimeCount(0);
            staffOntimeRateResp.setProcessTypeStaffAllCount(0);
            staffOntimeRateResp.setSumTheoryTime(0);
            staffOntimeRateResp.setWorkTime(0);
            staffOntimeRateResp.setRate(BigDecimal.ZERO);
            for(Map<String, Object> staffOnTime :staffOntimeSqlList){
                String processName = (String)staffOnTime.get("process_type");
                if(processName.equals(processTypeName)){
                    Integer countOnTime = (Integer) staffOnTime.get("countOnTime");
                    staffOntimeRateResp.setProcessTypeStaffOnTimeCount(countOnTime);
                }
            }
            for(Map<String, Object> countStaffClass :countStaffClassSqlList){
                String processtypeName = (String)countStaffClass.get("process_type");
                if(processTypeName.equals(processtypeName)){
                    Integer staffAllCount = (Integer) countStaffClass.get("staffAllCount");
                    staffOntimeRateResp.setProcessTypeStaffAllCount(staffAllCount);
                }
            }
            for(Map<String, Object> useRate: processTypeUseRateSqlList){
                String processName = (String)useRate.get("process_type");
                if(processName.equals(processTypeName)){
                    BigDecimal workTime =(BigDecimal) useRate.get("workTime") ;
                    Integer sumTheoryTime = ((BigDecimal) useRate.get("sumTheoryTime")).intValue();
                    staffOntimeRateResp.setWorkTime(workTime.intValue());
                    staffOntimeRateResp.setSumTheoryTime(sumTheoryTime);
                }
            }
            staffOntimeRateRespList.add(staffOntimeRateResp);
        }
        for(StaffOntimeRateResp resp : staffOntimeRateRespList){
            BigDecimal rate = caculateRate(resp.getProcessTypeStaffOnTimeCount(),resp.getProcessTypeStaffAllCount());
            BigDecimal useRate = caculateRate(resp.getSumTheoryTime(),resp.getWorkTime());
            resp.setUseRate(useRate);
            resp.setRate(rate);
        }
        return staffOntimeRateRespList;
    }



    @Override
    public ResultVo findProcessTaskByDevice(PcbTaskReq pcbTaskReq) {
        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\tt1.*,\n" +
                "\tt2.pcb_id,\n" +
                "\tt2.feeding_task_code,t2.model_name \n" +
                "FROM\n" +
                "\tproduce_process_task t1\n" +
                "\tLEFT JOIN produce_pcb_task t2 ON t2.pcb_task_code = t1.pcb_task_code \n" +
                "WHERE\n" +
                "\tt1.device_code LIKE '%" +
                pcbTaskReq.getDeviceCode() +
                "%' \n" +
                "\tAND ( t1.process_task_status = '生产中' OR t1.process_task_status LIKE '%已下达%' OR t1.process_task_status LIKE '%暂停%' OR t1.process_task_status LIKE '%进行中%' )\n" +
                "\tORDER BY t2.priority DESC ,t1.plan_start_time");
        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());

        return ResultVoUtil.success(mapList);
    }

    @Override
    public List<BadRateResp> processBadRate() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(xdate);
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";
        StringBuffer sql  = new StringBuffer("\tSELECT sum(amount_completed) sumamoumt FROM produce_process_task WHERE plan_finish_time >='" +
                startTime +
                "' and plan_finish_time <= '" +
                endTime +
                "'");


        StringBuffer badnumSql = new StringBuffer("SELECT\n" +
                "\tt2.process_type,\n" +
                "\tSUM ( t1.number ) badnum \n" +
                "FROM\n" +
                "\tbase_badpcb t1\n" +
                "\tLEFT JOIN base_process t2 ON t2.name= t1.process_name \n" +
                "WHERE\n" +
                "\tt1.update_date >= '" +
                startTime +
                "' \n" +
                "\tAND t1.update_date < = '" +
                endTime +
                "' \n" +
                "GROUP BY\n" +
                "\tt2.process_type");

        StringBuffer processTypeSql = new StringBuffer("\tSELECT process_type FROM base_process GROUP BY process_type\n");

        List<Map<String,Object>> processTypeMap = jdbcTemplate.queryForList(processTypeSql.toString());
        List<Map<String,Object>> badNum = jdbcTemplate.queryForList(badnumSql.toString());
        List<Map<String,Object>> sum = jdbcTemplate.queryForList(sql.toString());
        Integer allAmount = (Integer)sum.get(0).get("sumamoumt");
        List<BadRateResp> list =  new ArrayList<>();
        for(Map<String,Object> processType : processTypeMap){
            BadRateResp badRateResp = new BadRateResp();
            String processTypeName = (String)processType.get("process_type");
            badRateResp.setProcessType(processTypeName);
            badRateResp.setAllAmount(allAmount);
            badRateResp.setBadNum(0);
            badRateResp.setRate(BigDecimal.ZERO);
            for(Map<String,Object> bad : badNum){
                String badProcessType = (String)bad.get("process_type");
                if(badProcessType.equals(processTypeName)){
                    Integer badAmount = (Integer) bad.get("badnum") ;
                    badRateResp.setBadNum(badAmount);
                    badRateResp.setRate(caculateRate(badAmount,allAmount));
                }
            }
            list.add(badRateResp);
        }

        return list;
    }

    @Override
    public Device getDeviceByCode(String deviceCode) {

        return deviceRepository.findbyDeviceCode(deviceCode);
    }

    @Override
    public  List<DeviceRunTimeResp> getDeviceRunTime(String deviceCode) {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(xdate);
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";
        List<String> dayList = DateUtil.dayBetweenTwoDate(DateUtil.string2Date(startTime, ""),DateUtil.string2Date(endTime, ""));

        StringBuffer wheresql = new StringBuffer(" and device_status = 0 ");
        if(deviceCode!=null&&!"".equals(deviceCode)){
            wheresql.append(" and device_code = '" +
                    deviceCode +
                    "' ");
        }
        StringBuffer sql = new StringBuffer("SELECT CONVERT\n" +
                "\t( VARCHAR ( 100 ), start_time, 23 ) theday,\n" +
                "\tSUM ( continue_time ) runtime\n" +
                "FROM\n" +
                "\tbase_device_status_record \n" +
                "WHERE\n" +
                "\tstart_time >= '" +
                startTime +
                "' and start_time <= '" +
                endTime +
                "'  \n");

        sql.append(wheresql);
        sql.append("GROUP BY\n" +
                "\t( CONVERT ( VARCHAR ( 100 ), start_time, 23 ) )");
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        List<DeviceRunTimeResp> list = new ArrayList<>();
        for(String day : dayList){
            DeviceRunTimeResp resp = new DeviceRunTimeResp();
            resp.setTheDay(day);
            resp.setRunTime(0);
            for(Map<String, Object> map : mapList){
                String today = (String)map.get("theday");
                Integer runTime = (Integer)map.get("runtime");
                if(today.equals(day)){
                    resp.setRunTime(runTime/60);
                }
            }
            list.add(resp);
        }

        return list;
    }


    @Override
    public Map<String,Map<String,Object>> findByStartEndTimeBy3TiePian() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(xdate);
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";

        //正在进行的贴片任务
        StringBuffer runningSql = new StringBuffer("SELECT * FROM produce_process_task WHERE device_code like '%B16011%' and  is_now_flag = 1");

        StringBuffer waitingSql = new StringBuffer("SELECT * FROM produce_process_task WHERE device_code like '%B16011%' and process_task_status != '已完成' and process_task_status != '生产中' and process_task_status != '进行中' AND is_now_flag != 1  ORDER BY priority DESC ,plan_finish_time ");

        StringBuffer beiliaoSql = new StringBuffer("SELECT * FROM produce_process_task WHERE device_code = 'work0022' and is_now_flag = 1  ");

        List<Map<String,Object>> runningMapList = jdbcTemplate.queryForList(runningSql.toString());
        List<Map<String,Object>> waitingMapList = jdbcTemplate.queryForList(waitingSql.toString());
        List<Map<String,Object>> beiliaoMapList = jdbcTemplate.queryForList(beiliaoSql.toString());

        Map<String,Object> runningMap = runningMapList.size()!=0?runningMapList.get(0):new HashMap<>();
        Map<String,Object> waitingMap = waitingMapList.size()!=0?waitingMapList.get(0):new HashMap<>();
        Map<String,Object> beiliaoMap = beiliaoMapList.size()!=0?beiliaoMapList.get(0):new HashMap<>();

        Map<String,Map<String,Object>> result = new HashMap<>();
        result.put("running",runningMap);
        result.put("waiting",waitingMap);
        result.put("beiliao",beiliaoMap);

        return result;
    }

    @Override
    public User findOneLastOnTimeUser(String deviceCode) {

        List<UserDeviceHistory> histories = userDeviceHistoryRepository.findOneLastOnTime(deviceCode);
        if(histories==null||histories.size()==0){
            return  new User();
        }
        User user = userRepository.findById(histories.get(0).getUser_id()).get();
        if(user == null){
            return  new User();
        }
        Set<Role>  roleSet = roleService.getUserOkRoleList(user.getId());
        String roleNames = "";
        for(Role role : roleSet){
            roleNames = roleNames + role.getTitle() + "|";
        }
        user.setRoleNames(roleNames);

        return user;
    }

    @Override
    public Map<String,Object>  findCropRate(String deviceCode) {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        String today = DateUtil.date2String(xdate,"");
        List<DeviceCropRate> rateList = deviceCropRateRepository.findByDevice_codeAndRecord_time(deviceCode, today);
        Map<String,Object>  map = new HashMap<>();
        if(rateList!=null&&rateList.size()!=0){
            map.put("cropRate",rateList.get(0).getCrop_rate());
        }else {
            map.put("cropRate","0");
        }

        return map;
    }

    @Override
    public List<BadNews>  findBadNewRate() {
        Date date = new Date();
        Date xdate = DateUtil.dateAddHours(date,-1);
        Map<String,String> thisWeekDate = DateUtil.getThisWeek(xdate);
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";

        StringBuffer countTypesql = new StringBuffer("SELECT bad_type,COUNT(id) badCount FROM quality_badclass_detail where record_time >= '" +
                startTime +
                "' and record_time<= '" +
                endTime +
                "' GROUP BY bad_type  ");
        StringBuffer sumcountsql = new StringBuffer("SELECT COUNT(id) sumcount FROM quality_badclass_detail where record_time >= '" +
                startTime +
                "' and record_time<= '" +
                endTime +
                "' ");

        List<Map<String,Object>> sumCountMap = jdbcTemplate.queryForList(sumcountsql.toString());
        Integer sumcount = 1;
        if(sumCountMap!=null&&sumCountMap.size()!=0){
            sumcount = (Integer)sumCountMap.get(0).get("sumcount");
        }
        List<BadNews> list = badNewsRepository.findBybadType("炉后AOI");
        List<Map<String,Object>> countBadTypeMap = jdbcTemplate.queryForList(countTypesql.toString());
        for(BadNews badNews : list){
            badNews.setBad_rate(BigDecimal.ZERO);
            for(Map<String,Object> map : countBadTypeMap){
                String badType = (String)map.get("bad_type");
                Integer badCount = (Integer)map.get("badCount");
                if(badNews.getBad_name().equals(badType)){
                    BigDecimal rate = caculateRate(badCount, sumcount);
                    badNews.setBad_rate(rate);
                }
            }
        }

        return list;

    }

    @Override
    public User findOneLastOnTimeNotDown(String deviceCode) {
        UserDeviceHistory history = userDeviceHistoryRepository.findOneLastOnTimeNotDown(deviceCode);

        if(history==null){
            return  new User();
        }
        User user = userRepository.findById(history.getUser_id()).get();
        if(user == null){
            return  new User();
        }
        Set<Role>  roleSet = roleService.getUserOkRoleList(user.getId());
        String roleNames = "";
        for(Role role : roleSet){
            roleNames = roleNames + role.getTitle() + "|";
        }
        user.setRoleNames(roleNames);
        user.setUpTime(history.getUp_time());
        return user;
    }
}
