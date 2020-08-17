package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeviceSafeForm {
    private Integer safeId;
    @NotEmpty(message = "维护设备不能为空")
    private String safeDeviceCode;
    @NotEmpty(message = "维护类型不能为空")
    private String safeType;
    @NotEmpty(message = "维护内容不能为空")
    private String safeContent;
}
