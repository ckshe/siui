package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/09/14
 */
@Data
public class UserSignatureValid implements Serializable {
    @NotEmpty(message = "工号不能为空")
    private String cardSequence;
}