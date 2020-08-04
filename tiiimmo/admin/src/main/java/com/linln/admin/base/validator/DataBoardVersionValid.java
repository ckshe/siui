package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/07/07
 */
@Data
public class DataBoardVersionValid implements Serializable {
    @NotEmpty(message = "版本号不能为空")
    private String versionNo;
}