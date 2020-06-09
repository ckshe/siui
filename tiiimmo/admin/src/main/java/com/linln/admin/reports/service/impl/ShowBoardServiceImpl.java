package com.linln.admin.reports.service.impl;

import com.linln.admin.produce.domain.PcbTask;
import com.linln.admin.produce.domain.ProcessTask;
import com.linln.admin.produce.repository.PcbTaskRepository;
import com.linln.admin.produce.repository.ProcessTaskRepository;
import com.linln.admin.reports.service.ShowBoardService;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowBoardServiceImpl implements ShowBoardService {


    @Autowired
    private PcbTaskRepository pcbTaskRepository;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Override
    public List<PcbTask> pcbTaskBoard() {

        Map<String,String> thisWeekDate = DateUtil.getThisWeek(new Date());
        String startTime = thisWeekDate.get("weekBegin")+" 00:00:00";
        String endTime = thisWeekDate.get("weekEnd")+" 23:59:59";
        List<PcbTask> allByStartEndTime = pcbTaskRepository.findAllByStartEndTime(startTime, endTime);
        return allByStartEndTime;

    }

    @Override
    public Map<String,Object> getProcessTaskThisWeekProcess() {
        Map<String,Object> map = new HashMap<>();
        //本周即第四周
        Map<String,String> thisWeekDate4 = DateUtil.getThisWeek(new Date());
        String startTime4 = thisWeekDate4.get("weekBegin")+" 00:00:00";
        String endTime4 = thisWeekDate4.get("weekEnd")+" 23:59:59";
        List<ProcessTask> processTaskList4 = processTaskRepository.findByStartEndTime(startTime4,endTime4);
        int week4Finish = (int) processTaskList4.stream().filter(p -> "完成".equals(p.getProcess_task_status())).count();
        int week4All = processTaskList4.size()==0?1:processTaskList4.size();
        BigDecimal finish4 = new BigDecimal(week4Finish);
        BigDecimal all4 = new BigDecimal(week4All);
        BigDecimal rate4 = finish4.divide(all4).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));


        //第三周
        Date week3 = DateUtil.dateAddNum(new Date(),-7);
        Map<String,String> thisWeekDate3 = DateUtil.getThisWeek(week3);
        String startTime3 = thisWeekDate3.get("weekBegin")+" 00:00:00";
        String endTime3 = thisWeekDate3.get("weekEnd")+" 23:59:59";
        List<ProcessTask> processTaskList3 = processTaskRepository.findByStartEndTime(startTime3,endTime3);
        int week3Finish = (int) processTaskList3.stream().filter(p -> "完成".equals(p.getProcess_task_status())).count();
        int week3All = processTaskList3.size()==0?1:processTaskList3.size();
        BigDecimal finish3 = new BigDecimal(week3Finish);
        BigDecimal all3 = new BigDecimal(week3All);
        BigDecimal rate3 = finish3.divide(all3).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));


        //第二周
        Date week2 = DateUtil.dateAddNum(new Date(),-14);
        Map<String,String> thisWeekDate2 = DateUtil.getThisWeek(week2);
        String startTime2 = thisWeekDate2.get("weekBegin")+" 00:00:00";
        String endTime2 = thisWeekDate2.get("weekEnd")+" 22:59:59";
        List<ProcessTask> processTaskList2 = processTaskRepository.findByStartEndTime(startTime2,endTime2);
        int week2Finish = (int) processTaskList2.stream().filter(p -> "完成".equals(p.getProcess_task_status())).count();
        int week2All = processTaskList2.size()==0?1:processTaskList2.size();
        BigDecimal finish2 = new BigDecimal(week2Finish);
        BigDecimal all2 = new BigDecimal(week2All);
        BigDecimal rate2 = finish2.divide(all2).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));


        //第一周
        Date week1 = DateUtil.dateAddNum(new Date(),-21);
        Map<String,String> thisWeekDate1 = DateUtil.getThisWeek(week1);
        String startTime1 = thisWeekDate1.get("weekBegin")+" 00:00:00";
        String endTime1 = thisWeekDate1.get("weekEnd")+" 11:59:59";
        List<ProcessTask> processTaskList1 = processTaskRepository.findByStartEndTime(startTime1,endTime1);
        int week1Finish = (int) processTaskList1.stream().filter(p -> "完成".equals(p.getProcess_task_status())).count();
        int week1All = processTaskList1.size()==0?1:processTaskList1.size();
        BigDecimal finish1 = new BigDecimal(week1Finish);
        BigDecimal all1 = new BigDecimal(week1All);
        BigDecimal rate1 = finish1.divide(all1).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));

        map.put("第一周",rate1);
        map.put("第二周",rate2);
        map.put("第三周",rate3);
        map.put("第四周",rate4);
        return map;
    }
}
