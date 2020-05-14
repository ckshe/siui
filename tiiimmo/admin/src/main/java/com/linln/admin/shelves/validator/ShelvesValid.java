package com.linln.admin.shelves.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/14
 */
@Data
public class ShelvesValid implements Serializable {
    @NotEmpty(message = "货架名称不能为空")
    private String name;
}