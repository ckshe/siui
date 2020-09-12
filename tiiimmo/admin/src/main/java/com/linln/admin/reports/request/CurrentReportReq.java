package com.linln.admin.reports.request;

import lombok.Data;

@Data
public class CurrentReportReq extends  ReportReq {
    private Long id;
    //流通表类型
    private String reportType;

    //排产计划号
    private String pcbTaskCode;

    //规格型号
    private String pcbCode;

    //工序计划号
    private String processTaskCode;

    //生产批次号
    private String taskSheetCode;

    //年月
    private String yearMonth;
}
