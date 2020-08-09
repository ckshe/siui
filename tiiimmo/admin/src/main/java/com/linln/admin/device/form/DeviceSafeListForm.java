package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeviceSafeListForm {
    @NotNull(message = "页码不能为空")
    private Integer page;
    @NotNull(message = "每页记录数不能为空")
    private Integer size;
    private String safeDeviceCode;
    private String safeType;
    private String safeContent;
}
