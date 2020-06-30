package com.linln.admin.produce.service;


import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.common.vo.ResultVo;

public interface ProcessTaskService {

    ResultVo findProcessTask(ProcessTaskReq req);
}
