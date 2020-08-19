package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/08/18
 */
@Data
public class DeviceCraftParameterValid implements Serializable {
    @NotEmpty(message = "设备编号不能为空")
    private String device_code;
}