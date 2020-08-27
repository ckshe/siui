package com.linln.admin.system.validator;

import com.linln.modules.system.domain.Dept;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Data
public class UserValid implements Serializable {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "用户昵称不能为空")
    @Size(min = 2, message = "用户昵称：请输入至少2个字符")
    private String nickname;
    private String confirm;
    @NotNull(message = "所在部门不能为空")
    private Dept dept;
    @Pattern(regexp = "^\\s*$|^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    private String phone;

    @Pattern(regexp = "^\\s*$|^[a-z0-9._%-]+@([a-z0-9-]+\\.)+[a-z]{2,4}$|^1[3|4|5|7|8]\\d{9}$",message = "邮箱格式错误")
    private String email;
}
