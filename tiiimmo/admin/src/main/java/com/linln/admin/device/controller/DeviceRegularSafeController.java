package com.linln.admin.device.controller;

import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceRegularSafeForm;
import com.linln.admin.device.formModel.DeviceRegularSafeEditFormModel;
import com.linln.admin.device.realization.DeviceRegularSafeReal;
import com.linln.admin.device.resultVO.DeviceRegularSafeResultVO;
import com.linln.admin.device.resultVO.ResultVO;
import com.linln.admin.device.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@CrossOrigin()
@RestController
@RequestMapping("/deviceRegularSafe")
@Slf4j
public class DeviceRegularSafeController {
    private DeviceRegularSafeReal deviceRegularSafeReal;

    @Autowired
    public void setDeviceRegularSafeReal(DeviceRegularSafeReal deviceRegularSafeReal){
        this.deviceRegularSafeReal = deviceRegularSafeReal;
    }

    @RequestMapping("/get")
    public ResultVO<Object> getDeviceRegularSafesByDate(@Valid @RequestBody DeviceRegularSafeForm deviceRegularSafeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【获取设备定期检测内容】参数不正确，deviceRegularSafeForm={}", deviceRegularSafeForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        DeviceRegularSafeResultVO deviceRegularSafeResultVO = deviceRegularSafeReal.getDeviceRegularSafesByDate(deviceRegularSafeForm);
        return ResultVOUtil.success(deviceRegularSafeResultVO);
    }

    @RequestMapping("/edit")
    public ResultVO<Object> editDeviceRegularSafe(@Valid @RequestBody DeviceRegularSafeEditFormModel deviceRegularSafeEditFormModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【编辑设备日常维护内容】参数不正确，deviceRegularSafeEditFormModel={}", deviceRegularSafeEditFormModel.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        deviceRegularSafeReal.editDeviceRegularSafe(deviceRegularSafeEditFormModel);
        return ResultVOUtil.success(null);
    }

    /*@RequestMapping("/list")
    public ResultVO<Object> getDeviceRegularSafes()*/
}
