package com.linln.admin.produce.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 小懒虫
 * @date 2020/05/16
 */
@Data
public class TaskSheetValid implements Serializable {
    @NotEmpty(message = "制造编号不能为空")
    private String task_sheet_code;
}