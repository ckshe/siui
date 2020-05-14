package com.linln.admin.base.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author www
 * @date 2020/05/14
 */
@Data
public class DeviceTypeValid implements Serializable {
    @NotEmpty(message = "类型编号不能为空")
    private String code;
}