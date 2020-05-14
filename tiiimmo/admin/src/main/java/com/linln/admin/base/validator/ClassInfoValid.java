package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/14
 */
@Data
public class ClassInfoValid implements Serializable {
    @NotEmpty(message = "班次编码不能为空")
    private String code;
}