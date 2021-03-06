package com.linln.RespAndReqs;

import com.linln.admin.produce.domain.ProcessTaskDetail;
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
    //拼板数
    private Integer multiple;

    private Integer page;
    private Integer size;
    private String pcbTaskCode;
    //private String processTaskCode;
    private String process_task_code;
    private String taskSheetCode;

    private List<ProcessTaskDetail> detailList;
    private String pcbName; // 物料名称
    private String pcbCode; // 规格型号
    private List<detailDeviceReq> detailDeviceList;

    // 查询时的时间区间参数
    // 计划开始
    private Date planStartTimeBegin;
    private Date planStartTimeOver;
    // 计划完成
    private Date planFinishTimeBegin;
    private Date planFinishTimeOver;
    // 实际开始
    private Date startTimeBegin;
    private Date startTimeOver;
    // 实际完成
    private Date finishTimeBegin;
    private Date finishTimeOver;



    @Data
    public static class detailDeviceReq{
        private String device_code;
        private Integer plan_count;
        private Integer finish_count;
        private Date plan_day_time;
        private String process_task_code;

    }




}
