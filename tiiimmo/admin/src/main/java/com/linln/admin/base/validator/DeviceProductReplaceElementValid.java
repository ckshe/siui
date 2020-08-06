package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author ww
 * @date 2020/08/01
 */
@Data
public class DeviceProductReplaceElementValid implements Serializable {
    @NotEmpty(message = "规格型号不能为空")
    private String pcb_code;
}