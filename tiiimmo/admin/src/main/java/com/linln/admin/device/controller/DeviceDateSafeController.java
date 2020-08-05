package com.linln.admin.device.controller;

import com.linln.admin.device.VO.DeviceDateSafeVO;
import com.linln.admin.device.entity.DeviceDateSafe;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceDateSafeForm;
import com.linln.admin.device.form.DeviceSafeForm;
import com.linln.admin.device.formModel.DeviceDateSafeEditFormModel;
import com.linln.admin.device.realization.DeviceDateSafeReal;
import com.linln.admin.device.resultVO.DeviceDateSafeResultVO;
import com.linln.admin.device.resultVO.ResultVO;
import com.linln.admin.device.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@CrossOrigin()
@RestController
@RequestMapping("/deviceDateSafe")
@Slf4j
public class DeviceDateSafeController {
    private DeviceDateSafeReal deviceDateSafeReal;

    @Autowired
    public void setDeviceDateSafeReal(DeviceDateSafeReal deviceDateSafeReal){
        this.deviceDateSafeReal = deviceDateSafeReal;
    }

    @GetMapping("/list")
    public ResultVO<Object> getDeviceDateSafes(DeviceDateSafeForm deviceDateSafeForm){
        DeviceDateSafe deviceDateSafe = new DeviceDateSafe();
        BeanUtils.copyProperties(deviceDateSafeForm, deviceDateSafe);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("safeTime", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("safeDeviceCode", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<DeviceDateSafe> deviceDateSafeExample = Example.of(deviceDateSafe, matcher);
        List<DeviceDateSafeVO> deviceDateSafeVOS = deviceDateSafeReal.getDeviceDateSafes(deviceDateSafeExample);
        return ResultVOUtil.success(deviceDateSafeVOS);
    }

    @RequestMapping("/get")
    public ResultVO<Object> getDeviceDateSafesByDate(@Valid @RequestBody DeviceDateSafeForm deviceDateSafeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【获取设备日常维护内容】参数不正确，deviceDateSafeForm={}", deviceDateSafeForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        DeviceDateSafeResultVO deviceDateSafeResultVO = deviceDateSafeReal.getDeviceDateSafesByDate(deviceDateSafeForm);
        return ResultVOUtil.success(deviceDateSafeResultVO);
    }

    @RequestMapping("/edit")
    public ResultVO<Object> editDeviceDateSafes(@Valid @RequestBody DeviceDateSafeEditFormModel deviceDateSafeEditFormModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【编辑设备日常维护内容】参数不正确，deviceDateSafeEditFormModel={}", deviceDateSafeEditFormModel.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        deviceDateSafeReal.editDeviceDateSafes(deviceDateSafeEditFormModel);
        return ResultVOUtil.success(null);
    }
}
