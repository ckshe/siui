package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/31
 */
@Data
public class SkillValid implements Serializable {
    @NotEmpty(message = "技能编码不能为空")
    private String skill_code;
}