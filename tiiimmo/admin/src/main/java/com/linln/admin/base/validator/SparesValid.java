package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 连
 * @date 2020/06/10
 */
@Data
public class SparesValid implements Serializable {
    @NotEmpty(message = "条形编码不能为空")
    private String code;
    @NotEmpty(message = "耗材名称不能为空")
    private String name;
}