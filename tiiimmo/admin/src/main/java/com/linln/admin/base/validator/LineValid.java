package com.linln.admin.base.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author ww
 * @date 2020/05/14
 */
@Data
public class LineValid implements Serializable {
    @NotEmpty(message = "产线编号不能为空")
    private String code;
}