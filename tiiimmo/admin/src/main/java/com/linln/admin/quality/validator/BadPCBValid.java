package com.linln.admin.quality.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Data
public class BadPCBValid implements Serializable {
    @NotEmpty(message = "制造编码不能为空")
    private String manufactureCode;
}