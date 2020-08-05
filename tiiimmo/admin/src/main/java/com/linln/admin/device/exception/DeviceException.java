package com.linln.admin.device.exception;

import com.linln.admin.device.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceException extends RuntimeException {
    private String code;
    public DeviceException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public DeviceException(String code, String message) {
        super(message);
        this.code = code;
    }
}
