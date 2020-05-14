package com.linln.admin.fixture.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/14
 */
@Data
public class EditFixtureValid implements Serializable {
    @NotEmpty(message = "光板号不能为空")
    private String plateNo;
}