package com.linln.admin.environmentRecordTime.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/13
 */
@Data
public class EnvironmentRecordTimeValid implements Serializable {
    @NotEmpty(message = "编号不能为空")
    private String code;
}