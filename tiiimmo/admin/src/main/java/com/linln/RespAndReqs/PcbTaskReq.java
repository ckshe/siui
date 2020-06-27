package com.linln.RespAndReqs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PcbTaskReq {
    private Long pcbTaskId;
    private String plateNo;
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
    private String checkPositionSort;
    private Integer amount;
    private List<PcbTaskReq> data;
    private String reCount;
    private String processTaskStatus;
    private String processTaskCode;
    private String pcbTaskCode;
    private String pcbId;
    private String pcbName;
    private List<PcbTaskReq> badNewsList;
    private String badNews;
    private BigDecimal workTime;
    private String taskSheetCode;
    private String productCode;
    private String username;
    private String reCounttimeStamp;

}
