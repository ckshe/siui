package com.linln.admin.device.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceUseHistoryVO {
    private Integer deviceHistoryId;
    //生产任务单号
    private String pcbTaskCode;
    //生产批次
    private String taskSheetCode;
    //规格型号
    private String pcbCode;
    //设备编号
    private String deviceCode;
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date useStartTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date useEndTime;
    //使用人
    private String usePerson;
    //使用时间
    private Float useTime;
    //生产数量
    private Integer amount;
    //使用情况
    private String useResult;
    //备注
    private String remark;
}
