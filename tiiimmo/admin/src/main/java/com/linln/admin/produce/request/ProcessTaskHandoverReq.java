package com.linln.admin.produce.request;

import lombok.Data;

@Data
public class ProcessTaskHandoverReq {
    private String processTaskCode;

    private String deviceCode;
}
