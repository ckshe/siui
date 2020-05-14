package com.linln.admin.base.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author www
 * @date 2020/05/14
 */
@Data
public class DeviceValid implements Serializable {
    @NotEmpty(message = "所属厂区不能为空")
    private String belong_plant_area;
}