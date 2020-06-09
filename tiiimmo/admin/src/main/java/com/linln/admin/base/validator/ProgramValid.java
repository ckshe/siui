package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 连
 * @date 2020/06/09
 */
@Data
public class ProgramValid implements Serializable {
    @NotEmpty(message = "设备编号不能为空")
    private String code;
    @NotEmpty(message = "设备名称不能为空")
    private String name;
}