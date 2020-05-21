package com.linln.admin.equipmentMonitoring.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Data
public class ProductionStatusValid implements Serializable {
    @NotEmpty(message = "设备编码不能为空")
    private String deviceCode;
}