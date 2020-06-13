package com.linln.admin.produce.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author ww
 * @date 2020/06/13
 */
@Data
public class ProcessTheoryTimeValid implements Serializable {
    @NotEmpty(message = "规格型号不能为空")
    private String pcb_code;
}