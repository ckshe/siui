package com.linln.admin.maintenance.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Data
public class MaintenanceTableValid implements Serializable {
    @NotEmpty(message = "检查气压及阀门情况不能为空")
    private String hardwareStatus;
}