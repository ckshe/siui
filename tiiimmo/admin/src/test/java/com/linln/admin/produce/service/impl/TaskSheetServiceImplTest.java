package com.linln.admin.produce.service.impl;

import com.linln.admin.produce.domain.ProcessTaskDetail;
import com.linln.admin.produce.service.ProcessTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskSheetServiceImplTest {

    @Autowired
    private ProcessTaskService processTaskService;

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
        processTaskService.addTaskDetailList(list);
        System.out.println("");
    }

    @Test
    public void findProcessTaskDeatilList() {
        List<ProcessTaskDetail> processTaskDeatilList = processTaskService.findProcessTaskDeatilList("66852");
        System.out.println("");
    }
}