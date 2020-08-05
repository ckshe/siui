package com.linln.admin.device.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    MENU_EXIST("EX1001", "错误发生"),
    DEVICE_SAFE_IS_EXIST("EX1002", "设备维护内容已存在"),
    PARAM_ERROR("EX1003", "参数异常"),
    DEVICE_SAFE_ID_NOT_NULL("EX1004", "设备维护内容id不能为空"),
    DEVICE_SAFE_NOT_EXIST("EX1005", "设备维护内容不存在"),
    DATE_FORMAT_ERROR("EX1006", "日期转换异常"),
    DEVICE_SAFE_EDIT_IS_EXIST("EX1007", "该设备修改的内容已存在")
    ;
    private String code;
    private String message;
    ResultEnum(String code, String message) {
        this.code=code;
        this.message=message;
    }
}
