package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FirstQualityListForm {
    @NotNull(message = "页码不能为空")
    private Integer page;
    @NotNull(message = "每页记录数不能为空")
    private Integer size;
    //生产任务单号
    private String pcbTaskCode;
    //批次
    private String taskSheetCode;
    //规格型号
    private String pcbCode;
}
