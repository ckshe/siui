package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/14
 */
@Data
public class DeviceTechnologyValid implements Serializable {
    @NotEmpty(message = "参数编号不能为空")
    private String param_code;
}