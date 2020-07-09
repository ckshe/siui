package com.linln.admin.produce.service.impl;

import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.RespAndReqs.responce.PlateNoInfo;
import com.linln.admin.produce.domain.ProcessTaskDetail;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.admin.produce.service.ProcessTaskService;
import com.linln.common.vo.ResultVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskSheetServiceImplTest {

    @Autowired
    private ProcessTaskService processTaskService;

    @Autowired
    private PcbTaskService pcbTaskService;

    @Test
    public void addProcessTaskDetailList(){
        ProcessTaskDetail detail = new ProcessTaskDetail();
        detail.setDetail_type("人分配");
        detail.setFinish_count(0);
        detail.setPlan_count(2);
        detail.setProcess_task_code("66852");
        detail.setPlan_day_time(new Date());
        detail.setUser_name("dsa");

        ProcessTaskDetail detail2 = new ProcessTaskDetail();
        detail2.setDetail_type("人分配");
        detail2.setFinish_count(0);
        detail2.setPlan_count(2);
        detail2.setProcess_task_code("66852");
        detail2.setPlan_day_time(new Date());
        detail2.setUser_name("dsa");
        List<ProcessTaskDetail> list = new ArrayList<>();
        list.add(detail);
        list.add(detail2);
        //processTaskService.addTaskDetailList(list);
        System.out.println("");
    }

    @Test
    public void findProcessTaskDeatilList() {
        List<ProcessTaskDetail> processTaskDeatilList = processTaskService.findProcessTaskDeatilList("66852");
        System.out.println("");
    }


    @Test
    public void generateBatchId(){
        ResultVo resultVo = pcbTaskService.generateBatchId(7201L);
        System.out.println(resultVo.toString());
    }
    @Test
    public void putinto(){
        PlateNoInfo plateNoInfo = new PlateNoInfo();
        plateNoInfo.setPcbTaskId(7201L);
        plateNoInfo.setPcbCode("DCY2.908.1678GS-RA");
        plateNoInfo.setBiginNum(100);
        plateNoInfo.setPrefix("1678A");
        plateNoInfo.setSuffix("R");
        ResultVo resultVo = pcbTaskService.putIntoProduceByPlateInfo(plateNoInfo);
        System.out.println(resultVo.toString());
    }

    @Test
    public void deviceProduceAmount(){
        PcbTaskReq req = new PcbTaskReq();
        req.setReCount("0");
        req.setAmountCompleted(11);
        req.setDeviceCode("7");
        req.setStatus("1");
        req.setReCounttimeStamp("2020-07-09");
        req.setAmount(55);
        PcbTaskReq freq = new PcbTaskReq();
        List<PcbTaskReq> list = new ArrayList<>();
        list.add(req);
        freq.setData(list);
        Map<String, Object> map = pcbTaskService.deviceProduceAmount(freq);
        System.out.println("");
    }
}