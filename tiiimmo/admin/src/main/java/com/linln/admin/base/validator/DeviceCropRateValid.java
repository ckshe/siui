package com.linln.admin.base.validator;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author ww
 * @date 2020/06/19
 */
@Data
public class DeviceCropRateValid implements Serializable {
    @NotEmpty(message = "机台编号不能为空")
    private String device_code;
    @NotEmpty(message = "稼动率不能为空")
    private String crop_rate;

}