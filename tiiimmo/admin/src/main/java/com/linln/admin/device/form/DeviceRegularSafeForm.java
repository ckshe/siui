package com.linln.admin.device.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DeviceRegularSafeForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "本次检测时间不能为空")
    private Date thisSafeTime;
    @NotEmpty(message = "设备编号不能为空")
    private String deviceCode;
}
