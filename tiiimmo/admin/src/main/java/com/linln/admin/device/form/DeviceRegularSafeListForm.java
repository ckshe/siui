package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DeviceRegularSafeListForm {
    @NotNull(message = "页码不能为空")
    private Integer page;
    @NotNull(message = "每页记录数不能为空")
    private Integer size;
    private Date regularSafeTimeStart;
    private Date regularSafeTimeEnd;
    private String deviceCode;
}
