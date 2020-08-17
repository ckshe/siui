package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeviceRegularSafeContentListForm {
    @NotNull(message = "页码不能为空")
    private Integer page;
    @NotNull(message = "每页记录数不能为空")
    private Integer size;
    private String deviceCode;
    private String deviceRegularSafeContent;
}
