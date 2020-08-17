package com.linln.admin.device.controller;

import com.linln.admin.device.VO.DeviceSafeVO;
import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceSafeForm;
import com.linln.admin.device.form.DeviceSafeListForm;
import com.linln.admin.device.realization.DeviceSafeReal;
import com.linln.admin.device.resultVO.DeviceSafeResultVO;
import com.linln.admin.device.resultVO.ResultVO;
import com.linln.admin.device.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@CrossOrigin()
@RestController
@RequestMapping("/deviceSafe")
@Slf4j
public class DeviceSafeController {
    private DeviceSafeReal deviceSafeReal;

    @Autowired
    public void setDeviceSafeReal(DeviceSafeReal deviceSafeReal) {
        this.deviceSafeReal = deviceSafeReal;
    }

    @RequestMapping("/create")
    public ResultVO<Object> createDeviceSafe(@Valid @RequestBody DeviceSafeForm deviceSafeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建设备维护内容】参数不正确，deviceSafeForm={}", deviceSafeForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        deviceSafeForm.setSafeId(null);
        deviceSafeReal.createDeviceSafe(deviceSafeForm);
        return ResultVOUtil.success(null);
    }

    @RequestMapping("/list")
    public ResultVO<Object> getDeviceSafes(@Valid @RequestBody DeviceSafeListForm deviceSafeListForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【查询设备维护内容】参数不正确，deviceSafeForm={}", deviceSafeListForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        DeviceSafe deviceSafe = new DeviceSafe();
        BeanUtils.copyProperties(deviceSafeListForm, deviceSafe);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("safeDeviceCode", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("safeType", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("safeContent", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<DeviceSafe> deviceSafeExample = Example.of(deviceSafe, matcher);
        Pageable pageable = PageRequest.of(deviceSafeListForm.getPage()-1, deviceSafeListForm.getSize());
        DeviceSafeResultVO deviceSafeResultVO = deviceSafeReal.getDeviceSafes(deviceSafeExample, pageable);
        return ResultVOUtil.success(deviceSafeResultVO);
    }

    @RequestMapping("/edit")
    public ResultVO<Object> editDeviceSafe(@Valid @RequestBody DeviceSafeForm deviceSafeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建设备维护内容】参数不正确，deviceSafeForm={}", deviceSafeForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        if(deviceSafeForm.getSafeId()==null){
            log.error("【创建设备维护内容】设备维护内容id不能为空，deviceSafeForm={}", deviceSafeForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_SAFE_ID_NOT_NULL);
        }
        deviceSafeReal.editDeviceSafe(deviceSafeForm);
        return ResultVOUtil.success(null);
    }

    @GetMapping("/delete")
    public ResultVO<Object> deleteDeviceSafe(Integer safeId){
        if(safeId==null){
            log.error("【删除设备维护内容】设备维护内容id不能为空，id={}", safeId);
            throw new DeviceException(ResultEnum.DEVICE_SAFE_ID_NOT_NULL);
        }
        deviceSafeReal.deleteDeviceSafe(safeId);
        return ResultVOUtil.success(null);
    }
}
