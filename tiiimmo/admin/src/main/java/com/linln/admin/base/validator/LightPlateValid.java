package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 连
 * @date 2020/06/09
 */
@Data
public class LightPlateValid implements Serializable {
    @NotEmpty(message = "规格型号不能为空")
    private String type;
    @NotEmpty(message = "物料不能为空")
    private String name;
}