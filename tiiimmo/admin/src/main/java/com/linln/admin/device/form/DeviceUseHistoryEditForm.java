package com.linln.admin.device.form;

import lombok.Data;

import java.util.Date;
@Data
public class DeviceUseHistoryEditForm {
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
    private Date useStartTime;
    //结束时间
    private Date useEndTime;
    //使用人
    private String usePerson;
    //使用时间
    private Date useTime;
    //生产数量
    private Integer amount;
    //使用情况
    private String useResult;
    //备注
    private String remark;
}
