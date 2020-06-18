package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author ww
 * @date 2020/06/17
 */
@Data
public class DeviceProductElementValid implements Serializable {
    @NotEmpty(message = "图样名不能为空")
    private String sample_name;
}