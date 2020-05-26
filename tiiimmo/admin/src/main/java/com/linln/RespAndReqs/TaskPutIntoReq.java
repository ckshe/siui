package com.linln.RespAndReqs;

import lombok.Data;

import java.util.Date;

@Data
public class TaskPutIntoReq {
    private Long pcbTaskId;
    private Date startTime;
    private Date finishTime;
    private String deviceName;
    private String deviceCode;

    private Long processTaskId;

}
