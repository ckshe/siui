package com.linln.RespAndReqs;

import lombok.Data;

import java.util.Date;

@Data
public class PcbTaskReq {
    private Long pcbTaskId;
    private Integer amountCompleted;
    private Date startTime;
    private Date finishTime;
    private String deviceName;
    private String deviceCode;
    private Date planStartTime;
    private Date planFinishTime;
    private Long processTaskId;
    private String feedindTaskCode;
    private Integer page;
    private Integer size;

}
