package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 连
 * @date 2020/06/09
 */
@Data
public class SubstitutesValid implements Serializable {
    @NotEmpty(message = "物料代码不能为空")
    private String code;
    @NotEmpty(message = "物料名称不能为空")
    private String name;
}