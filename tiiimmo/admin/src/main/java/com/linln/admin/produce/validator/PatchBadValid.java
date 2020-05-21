package com.linln.admin.produce.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/21
 */
@Data
public class PatchBadValid implements Serializable {
    @NotEmpty(message = "版编号不能为空")
    private String pcb_code;
}