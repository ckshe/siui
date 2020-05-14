package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 小懒虫
 * @date 2020/05/13
 */
@Data
public class VehicleValid implements Serializable {
    @NotEmpty(message = "编码不能为空")
    private String code;
}