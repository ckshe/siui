package com.linln.admin.base.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author www
 * @date 2020/05/13
 */
@Data
public class ProcessValid implements Serializable {
    @NotEmpty(message = "编号不能为空")
    private String code;
}