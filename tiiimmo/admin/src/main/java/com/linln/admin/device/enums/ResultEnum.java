package com.linln.admin.device.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    DEVICE_SAFE_IS_EXIST("EX1002", "设备维护内容已存在"),
    PARAM_ERROR("EX1003", "参数异常"),
    DEVICE_SAFE_ID_NOT_NULL("EX1004", "设备维护内容id不能为空"),
    DEVICE_SAFE_NOT_EXIST("EX1005", "设备维护内容不存在"),
    DATE_FORMAT_ERROR("EX1006", "日期转换异常"),
    DEVICE_SAFE_EDIT_IS_EXIST("EX1007", "该设备修改的内容已存在"),
    DEVICE_REGULAR_SAFE_CONTENT_IS_EXIST("EX1008", "该定期检测内容已存在"),
    DEVICE_NOT_EXIST("EX1009","设备不存在"),
    DEVICE_REGULAR_SAFE_CONTENT_ID_NOT_NULL("EX1010","定期检测内容id不能为空"),
    DEVICE_REGULAR_SAFE_CONTENT_NOT_EXIST("EX1011","该定期检测内容不存在"),
    DEVICE_REGULAR_SAFE_ID_NOT_NULL("EX1012", "定期检测内容id不能为空"),
    DEVICE_REGULAR_SAFE_NOT_EXIST("EX1013","设备定期检测内容不存在"),
    DEVICE_AMBIENT_NOT_EXIST("EX1014", "该环境记录不存在"),
    DEVICE_AMBIENT_ID_NOT_NULL("EX1015", "温湿度记录id不能为空")
    ;
    private String code;
    private String message;
    ResultEnum(String code, String message) {
        this.code=code;
        this.message=message;
    }
}
