package com.linln.admin.pda.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/14
 */
@Data
public class PdaValid implements Serializable {
    @NotEmpty(message = "名称不能为空")
    private String name;
}