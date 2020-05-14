package com.linln.admin.line.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author ww
 * @date 2020/05/14
 */
@Data
public class LineValid implements Serializable {
    @NotEmpty(message = "产线编号不能为空")
    private String code;
}