package com.linln.admin.project.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/21
 */
@Data
public class PcbTechnologyValid implements Serializable {
    @NotEmpty(message = "更改记录编码不能为空")
    private String pcb_technology_code;
}