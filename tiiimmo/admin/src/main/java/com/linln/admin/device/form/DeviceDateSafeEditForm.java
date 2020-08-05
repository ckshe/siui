package com.linln.admin.device.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DeviceDateSafeEditForm {
    @NotNull(message = "id不能为空")
    private Integer id;
    private Date safeTime;
    private String safeType;
    private String safeDeviceCode;
    private String safeContent;
    private String safeResult;
    private String safePerson;
}
