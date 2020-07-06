package com.linln.admin.produce.service;


import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.admin.produce.domain.ProcessTaskDetail;
import com.linln.common.vo.ResultVo;

import java.util.List;

public interface ProcessTaskService {

    ResultVo findProcessTask(ProcessTaskReq req);


    //新增任务单详情
    void addTaskDetailList(List<ProcessTaskDetail> details);

    //查看工序任务单详情
    List<ProcessTaskDetail> findProcessTaskDeatilList(String processTaskCode);
}
