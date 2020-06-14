package com.linln.RespAndReqs.responce;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BadRateResp {
    private String processType;
    private Integer badNum;
    private Integer allAmount;
    private BigDecimal rate;
}
