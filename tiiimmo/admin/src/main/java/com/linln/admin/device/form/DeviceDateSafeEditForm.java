package com.linln.admin.device.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DeviceDateSafeEditForm {
    @NotNull(message = "id不能为空")
    private Integer dateSafeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "维护时间不能为空")
    private Date safeTime;
    @NotEmpty(message = "维护类型不能为空")
    private String safeType;
    @NotEmpty(message = "维护设备不能为空")
    private String safeDeviceCode;
    @NotEmpty(message = "维护内容不能为空")
    private String safeContent;
    private String safeResult;
    private String safePerson;
}
