package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FirstQualityForm {
    //生产任务单号
    @NotEmpty(message = "生产任务单号不能为空")
    private String pcbTaskCode;
    //批次
    @NotEmpty(message = "生产批次不能为空")
    private String taskSheetCode;
    //规格型号
    @NotEmpty(message = "规格型号不能为空")
    private String pcbCode;
}
