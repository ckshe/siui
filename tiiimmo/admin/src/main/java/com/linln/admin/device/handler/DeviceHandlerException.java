package com.linln.admin.device.handler;

import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.resultVO.ResultVO;
import com.linln.admin.device.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class DeviceHandlerException {
    @ExceptionHandler(value = DeviceException.class)
    @ResponseBody
    public ResultVO<Object> globalDeviceHandler(Exception e) {
        DeviceException ex = (DeviceException) e;
        return ResultVOUtil.error(ex.getCode(), ex.getMessage());
    }
}
