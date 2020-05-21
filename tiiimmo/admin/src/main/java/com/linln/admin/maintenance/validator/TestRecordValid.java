package com.linln.admin.maintenance.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Data
public class TestRecordValid implements Serializable {
    @NotEmpty(message = "设备编号不能为空")
    private String deviceCode;
}