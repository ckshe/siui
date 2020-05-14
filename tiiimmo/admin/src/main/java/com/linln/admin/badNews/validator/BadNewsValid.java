package com.linln.admin.badNews.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/13
 */
@Data
public class BadNewsValid implements Serializable {
    @NotEmpty(message = "不良编号不能为空")
    private String bad_code;
}