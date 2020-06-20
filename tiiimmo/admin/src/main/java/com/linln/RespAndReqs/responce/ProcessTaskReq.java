package com.linln.RespAndReqs.responce;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProcessTaskReq {
    private Long processId;
    private String processName;

    private Date planStartTime;
    private Date planFinishTime;
    private Date startTime;
    private Date finishTime;
    private Integer pcbQuantity;
    private Integer amountCompleted;
    private BigDecimal workTime;
    private String status;




}
