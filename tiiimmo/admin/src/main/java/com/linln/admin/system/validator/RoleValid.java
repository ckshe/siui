package com.linln.admin.system.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Data
public class RoleValid implements Serializable {
    @NotEmpty(message = "岗位编号不能为空")
    private String name;
    @NotEmpty(message = "岗位名称不能为空")
    private String title;
}
