package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
@Data
public class ModelsValid implements Serializable {
    @NotEmpty(message = "机型编码不能为空")
    private String model_id;
}