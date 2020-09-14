package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/09/14
 */
@Data
public class ProcessDocumentsValid implements Serializable {
    @NotEmpty(message = "规格型号不能为空")
    private String pcb_code;
}