package com.linln.admin.produce.service;


import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.admin.produce.domain.ProcessTaskDetail;
import com.linln.admin.produce.domain.ProcessTaskDetailDevice;
import com.linln.common.vo.ResultVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ProcessTaskService {

    ResultVo findProcessTask(ProcessTaskReq req);


    //新增任务单详情
    void addTaskDetailList(ProcessTaskReq req);

    //查看工序任务单详情
    List<ProcessTaskDetail> findProcessTaskDeatilList(String processTaskCode);

    //根据id删除工序任务单详情
    void deleteProcessTaskDetailById(Long id);
    //查看pdf
    void showPDF(HttpServletResponse response, String  deviceCode,String type);

    //根据规格型号查看ESOP
    void showESOPPDF(HttpServletResponse response, String pcbCode);

    List<ProcessTaskDetailDevice> findByTaskCodeAndDayTime(String processTaskCode,String planDayTime);

    List<ProcessTaskDetailDevice> findByTaskCodeAndDayTimeAndDeviceCode(String processTaskCode,String planDayTime,String deviceCode);

    ResultVo getProcessTaskDetailByPage(ProcessTaskReq req);


    ResultVo changeMultiple(ProcessTaskReq req);
}
