package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/14
 */
@Data
public class DeviceSortValid implements Serializable {
    @NotEmpty(message = "设备类型不能为空")
    private String device_type;
}