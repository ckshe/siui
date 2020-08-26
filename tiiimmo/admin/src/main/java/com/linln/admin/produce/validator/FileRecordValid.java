package com.linln.admin.produce.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/08/25
 */
@Data
public class FileRecordValid implements Serializable {
    @NotEmpty(message = "文件名不能为空")
    private String title;
}