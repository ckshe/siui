package com.linln.admin.quality.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/21
 */
@Data
public class FirstRecordDetailsValid implements Serializable {
    @NotEmpty(message = "环节不能为空")
    private String link;
}