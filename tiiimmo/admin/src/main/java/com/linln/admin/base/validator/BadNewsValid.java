package com.linln.admin.base.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author www
 * @date 2020/05/13
 */
@Data
public class BadNewsValid implements Serializable {
    @NotEmpty(message = "不良编号不能为空")
    private String bad_code;
}