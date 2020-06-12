package com.linln.RespAndReqs.responce;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProcessThisWeekRateResp {
    private String theDay;
    private Integer allCount;
    private Integer finishCount;
    private BigDecimal rate;
    private Integer sumFinishAmount;
}
