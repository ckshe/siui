package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DeviceRegularSafeContentForm {
    private Integer regularSafeContentId;
    @NotEmpty(message = "设备编号不能为空")
    private String deviceCode;
    @NotEmpty(message = "检测内容不能为空")
    private String deviceRegularSafeContent;
    private String company;
}
