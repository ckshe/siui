package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeviceRegularSafeResForm {
    @NotEmpty(message = "检测内容不能为空")
    //检测内容
    private String safeContent;
    //检测值
    private String safeResult;
    //单位
    private String company;
}
