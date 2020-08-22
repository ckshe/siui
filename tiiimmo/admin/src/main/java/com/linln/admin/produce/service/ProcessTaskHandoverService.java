package com.linln.admin.produce.service;

import com.linln.admin.produce.domain.ProcessTaskHandover;
import com.linln.admin.produce.request.ProcessTaskHandoverReq;
import com.linln.common.vo.ResultVo;

public interface ProcessTaskHandoverService {
    public void addProceeTaskHandover(ProcessTaskHandover handover);

    public ResultVo findPRocessTaskHandover(ProcessTaskHandoverReq req);



}
