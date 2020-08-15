package com.linln.admin.device.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DeviceDateSafeForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "维护时间不能为空")
    private Date safeTime;
    @NotEmpty(message = "维护类型不能为空")
    private String safeDeviceCode;
    private String safeType;
}
