package com.linln.admin.reports.service;

import com.linln.admin.produce.domain.CurrentReport;
import com.linln.admin.reports.request.BadDetailReq;
import com.linln.admin.reports.request.ClassInfoReq;
import com.linln.admin.reports.request.CurrentReportReq;
import com.linln.admin.reports.request.DevciceRepairRecordReq;
import com.linln.common.vo.ResultVo;

public interface ReportService {

    //不良记录报表
    public ResultVo badDetailReport(BadDetailReq req);

    //通用报表
    public ResultVo findCurrentReport(CurrentReportReq reportReq);

    //班次分析

    public ResultVo findClassInfo(ClassInfoReq req);

    //首件记录表合并
    public ResultVo findSJJLBReport(CurrentReportReq reportReq);

    //年月查询
    public ResultVo findYearMonth(DevciceRepairRecordReq req);
}
