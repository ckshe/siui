package com.linln.admin.device.controller;

import com.linln.admin.device.VO.DeviceUseHistoryVO;
import com.linln.admin.device.VO.FirstQualityVO;
import com.linln.admin.device.entity.DeviceUseHistory;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceUseHistoryForm;
import com.linln.admin.device.form.FirstQualityForm;
import com.linln.admin.device.formModel.FirstQualityEditFormModel;
import com.linln.admin.device.realization.DeviceUseHistoryReal;
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
    public ResultVO<Object> editDeviceUseHistory(@Valid @RequestBody FirstQualityEditFormModel firstQualityEditFormModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【编辑工单首检记录】参数不正确，deviceSafeForm={}", firstQualityEditFormModel.toString());
            throw new DeviceException(ResultEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        //firstQualityReal.editFirstQuality(firstQualityEditFormModel);
        return ResultVOUtil.success(null);
    }
}
