package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DeviceUseHistoryForm {
    private Integer deviceHistoryId;
    @NotEmpty(message = "生产任务单号不能为空")
    //生产任务单号
    private String pcbTaskCode;
    @NotEmpty(message = "生产批次不能为空")
    //生产批次
    private String taskSheetCode;
    @NotEmpty(message = "规格型号不能为空")
    //规格型号
    private String pcbCode;
    @NotEmpty(message = "设备编号不能为空")
    //设备编号
    private String deviceCode;
}
