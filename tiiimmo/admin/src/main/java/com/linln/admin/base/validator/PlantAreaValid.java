package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/14
 */
@Data
public class PlantAreaValid implements Serializable {
    @NotEmpty(message = "厂区编号不能为空")
    private String code;
}