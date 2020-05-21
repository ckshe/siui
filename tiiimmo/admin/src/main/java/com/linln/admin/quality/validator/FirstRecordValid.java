package com.linln.admin.quality.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Data
public class FirstRecordValid implements Serializable {
    @NotEmpty(message = "制造编号不能为空")
    private String manufactureNo;
}