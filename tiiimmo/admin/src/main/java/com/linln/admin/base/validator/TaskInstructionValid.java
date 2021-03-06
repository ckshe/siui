package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 连
 * @date 2020/06/09
 */
@Data
public class TaskInstructionValid implements Serializable {
    @NotEmpty(message = "作业指导书编号不能为空")
    private String code;
    @NotEmpty(message = "作业指导书名称不能为空")
    private String name;
}