package com.linln.admin.reports.service;

import com.linln.admin.produce.domain.CurrentReport;
import com.linln.admin.reports.request.BadDetailReq;
import com.linln.common.vo.ResultVo;

public interface ReportService {

    //不良记录报表
    public ResultVo badDetailReport(BadDetailReq req);

    public void addCurrentReport(CurrentReport currentReport);
}
