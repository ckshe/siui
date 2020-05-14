package com.linln.admin.classInfo.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/13
 */
@Data
public class ClassInfoValid implements Serializable {
    @NotEmpty(message = "物料编号不能为空")
    private String product_code;
}