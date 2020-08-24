package com.linln.admin.device.controller;

import com.linln.admin.device.VO.DeviceUseHistoryVO;
import com.linln.admin.device.VO.FirstQualityVO;
import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.entity.DeviceUseHistory;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.*;
import com.linln.admin.device.formModel.FirstQualityEditFormModel;
import com.linln.admin.device.realization.DeviceUseHistoryReal;
import com.linln.admin.device.resultVO.DeviceSafeResultVO;
import com.linln.admin.device.resultVO.DeviceUseHistoryListResultVO;
import com.linln.admin.device.resultVO.ResultVO;
import com.linln.admin.device.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@CrossOrigin()
@RestController
@RequestMapping("/deviceUseHistory")
@Slf4j
public class DeviceUseHistoryController {
    private DeviceUseHistoryReal deviceUseHistoryReal;

    @Autowired
    public void setDeviceUseHistoryReal(DeviceUseHistoryReal deviceUseHistoryReal){
        this.deviceUseHistoryReal = deviceUseHistoryReal;
    }

    @RequestMapping("/get")
    public ResultVO<Object> getDeviceUseHistory(@Valid @RequestBody DeviceUseHistoryForm deviceUseHistoryForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【获取设备使用历史记录】参数不正确，deviceUseHistoryForm={}", deviceUseHistoryForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        DeviceUseHistoryVO deviceUseHistoryVO = deviceUseHistoryReal.getDeviceUseHistory(deviceUseHistoryForm);
        return ResultVOUtil.success(deviceUseHistoryVO);
    }

    @RequestMapping("/edit")
    public ResultVO<Object> editDeviceUseHistory(@Valid @RequestBody DeviceUseHistoryEditForm deviceUseHistoryEditForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【编辑设备使用历史记录】参数不正确，deviceUseHistoryEditForm={}", deviceUseHistoryEditForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        deviceUseHistoryReal.editDeviceUseHistory(deviceUseHistoryEditForm);
        return ResultVOUtil.success(null);
    }

    @RequestMapping("/list")
    public ResultVO<Object> getDeviceUseHistoryList(@Valid @RequestBody DeviceUseHistoryListForm deviceUseHistoryListForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【查询设备使用历史列表】参数不正确，deviceSafeForm={}", deviceUseHistoryListForm.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        DeviceUseHistory deviceUseHistory = new DeviceUseHistory();
        BeanUtils.copyProperties(deviceUseHistoryListForm, deviceUseHistory);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pcbTaskCode", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("taskSheetCode", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("pcbCode", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("deviceCode", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<DeviceUseHistory> deviceUseHistoryExample = Example.of(deviceUseHistory, matcher);
        Pageable pageable = PageRequest.of(deviceUseHistoryListForm.getPage()-1, deviceUseHistoryListForm.getSize());
        DeviceUseHistoryListResultVO deviceUseHistoryListResultVO = deviceUseHistoryReal.getDeviceUseHistoryList(deviceUseHistoryExample, pageable);
        return ResultVOUtil.success(deviceUseHistoryListResultVO);
    }
}
