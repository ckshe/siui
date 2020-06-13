package com.linln.RespAndReqs.responce;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StaffOntimeRateResp {
    private String processType;
    private BigDecimal rate;
    private Integer processTypeStaffOnTimeCount;
    private Integer processTypeStaffAllCount;
}
