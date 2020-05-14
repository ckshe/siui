package com.linln.admin.base.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author www
 * @date 2020/05/14
 */
@Data
public class MouldPcbDetailValid implements Serializable {
    @NotEmpty(message = "PCB编码不能为空")
    private String pcb_code;
}