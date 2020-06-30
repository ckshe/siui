package com.linln.RespAndReqs;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProcessTaskReq {
    private Long processId;
    private String processName;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    //@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date planStartTime;
    private Date planFinishTime;
    private Date startTime;
    private Date finishTime;
    private Integer pcbQuantity;
    private Integer amountCompleted;
    private BigDecimal workTime;
    private String status;


    private Integer page;
    private Integer size;
    private String pcbTaskCode;
    private String taskSheetCode;




}