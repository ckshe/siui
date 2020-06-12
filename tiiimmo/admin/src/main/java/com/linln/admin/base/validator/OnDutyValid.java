package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/06/12
 */
@Data
public class OnDutyValid implements Serializable {
    @NotEmpty(message = "员工名称不能为空")
    private String userName;
}