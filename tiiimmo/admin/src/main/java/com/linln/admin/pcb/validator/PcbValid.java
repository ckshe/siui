package com.linln.admin.pcb.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/13
 */
@Data
public class PcbValid implements Serializable {
    @NotEmpty(message = "PCB编码不能为空")
    private String code;
}