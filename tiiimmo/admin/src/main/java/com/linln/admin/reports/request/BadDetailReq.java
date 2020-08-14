package com.linln.admin.reports.request;

import lombok.Data;

@Data
public class BadDetailReq extends ReportReq {
    private String pcbTaskCode;
    private String plateNo;
}
