package com.linln.admin.maintenance.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Data
public class MaintenanceContentValid implements Serializable {
    @NotEmpty(message = "设备类型不能为空")
    private String deviceType;
}