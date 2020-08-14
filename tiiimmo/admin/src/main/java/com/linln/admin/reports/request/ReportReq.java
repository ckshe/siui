package com.linln.admin.reports.request;

import lombok.Data;

@Data
public class ReportReq {
    private String startTime;
    private String endTime;
    private Integer size;
    private Integer page;
}
