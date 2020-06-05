package com.linln.RespAndReqs;

import lombok.Data;

import java.util.Date;
import java.util.List;

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
    private String timeStamp;
    private String status;
    private Integer amount;
    private List<PcbTaskReq> data;
    private String reCount;
    private String processTaskStatus;
    private String processTaskCode;
    private String pcbTaskCode;


}
