package com.linln.admin.device.realization;

import com.linln.admin.base.domain.Device;
import com.linln.admin.base.service.impl.DeviceServiceImpl;
import com.linln.admin.device.VO.DeviceRegularSafeResVO;
import com.linln.admin.device.entity.DeviceRegularSafe;
import com.linln.admin.device.entity.DeviceRegularSafeContent;
import com.linln.admin.device.entity.DeviceRegularSafeResult;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceRegularSafeForm;
import com.linln.admin.device.formModel.DeviceRegularSafeEditFormModel;
import com.linln.admin.device.resultVO.DeviceRegularSafeResultVO;
import com.linln.admin.device.serviceImpl.DeviceRegularSafeContentServiceImpl;
import com.linln.admin.device.serviceImpl.DeviceRegularSafeResultServiceImpl;
import com.linln.admin.device.serviceImpl.DeviceRegularSafeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceRegularSafeReal {
    private DeviceRegularSafeServiceImpl deviceRegularSafeService;
    private DeviceRegularSafeContentServiceImpl deviceRegularSafeContentService;
    private DeviceServiceImpl deviceService;
    private DeviceRegularSafeResultServiceImpl deviceRegularSafeResultService;

    @Autowired
    public void setDeviceRegularSafeService(DeviceRegularSafeServiceImpl deviceRegularSafeService) {
        this.deviceRegularSafeService = deviceRegularSafeService;
    }

    @Autowired
    public void setDeviceRegularSafeContentService(DeviceRegularSafeContentServiceImpl deviceRegularSafeContentService){
        this.deviceRegularSafeContentService = deviceRegularSafeContentService;
    }

    @Autowired
    public void setDeviceService(DeviceServiceImpl deviceService){
        this.deviceService = deviceService;
    }

    @Autowired
    public void setDeviceRegularSafeResultService(DeviceRegularSafeResultServiceImpl deviceRegularSafeResultService){
        this.deviceRegularSafeResultService = deviceRegularSafeResultService;
    }

    @Transactional
    public DeviceRegularSafeResultVO getDeviceRegularSafesByDate(DeviceRegularSafeForm deviceRegularSafeForm) {
        DeviceRegularSafeResultVO deviceRegularSafeResultVO = new DeviceRegularSafeResultVO();
        List<DeviceRegularSafe> deviceRegularSafes = deviceRegularSafeService.findByThisSafeTime(deviceRegularSafeForm.getThisSafeTime());
        List<DeviceRegularSafeResult> deviceRegularSafeResults = deviceRegularSafeResultService.findByTheSafeTimeAndDeviceCode(deviceRegularSafeForm.getThisSafeTime(), deviceRegularSafeForm.getDeviceCode());
        List<Device> devices = deviceService.list();
        devices = devices.stream().filter(e->e.getDevice_code().equals(deviceRegularSafeForm.getDeviceCode())).collect(Collectors.toList());
        if (devices.size() == 0){
            log.error("【获取设备定期检测内容】设备不存在，deviceRegularSafeForm={}", deviceRegularSafeForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_NOT_EXIST);
        }
        if (deviceRegularSafes.size() == 0) {
            List<DeviceRegularSafeContent> deviceRegularSafeContents = deviceRegularSafeContentService.findByDeviceCode(deviceRegularSafeForm.getDeviceCode());
            DeviceRegularSafe deviceRegularSafe = new DeviceRegularSafe();
            BeanUtils.copyProperties(deviceRegularSafeForm, deviceRegularSafe);
            deviceRegularSafes.add(deviceRegularSafe);
            for (DeviceRegularSafeContent deviceRegularSafeContent : deviceRegularSafeContents){
                DeviceRegularSafeResult deviceRegularSafeResult = new DeviceRegularSafeResult();
                BeanUtils.copyProperties(deviceRegularSafeContent, deviceRegularSafeResult);
                deviceRegularSafeResult.setTheSafeTime(deviceRegularSafe.getThisSafeTime());
                deviceRegularSafeResults.add(deviceRegularSafeResult);
            }
            deviceRegularSafe.setDeviceName(devices.get(0).getDevice_name());
            deviceRegularSafeService.saveDeviceRegularSafe(deviceRegularSafe);
            deviceRegularSafeResultService.saveDeviceRegularSafeResults(deviceRegularSafeResults);
        }
        BeanUtils.copyProperties(deviceRegularSafes.get(0), deviceRegularSafeResultVO);
        deviceRegularSafeResultVO.setDeviceName(devices.get(0).getDevice_name());
        List<DeviceRegularSafeResVO> deviceRegularSafeResVOS = new ArrayList<>();
        for (DeviceRegularSafeResult deviceRegularSafeResult : deviceRegularSafeResults){
            DeviceRegularSafeResVO deviceRegularSafeResVO = new DeviceRegularSafeResVO();
            BeanUtils.copyProperties(deviceRegularSafeResult, deviceRegularSafeResVO);
            deviceRegularSafeResVOS.add(deviceRegularSafeResVO);
        }
        deviceRegularSafeResultVO.setDeviceRegularSafeResVOS(deviceRegularSafeResVOS);
        return deviceRegularSafeResultVO;
    }

    @Transactional
    public void editDeviceRegularSafe(DeviceRegularSafeEditFormModel deviceRegularSafeEditFormModel){
        DeviceRegularSafe deviceRegularSafe = deviceRegularSafeService.findById(deviceRegularSafeEditFormModel.getRegularSafeId());
        if (deviceRegularSafe==null){
            log.error("【编辑设备定期检测内容】设备定期检测内容不存在，deviceRegularSafeEditFormModel={}", deviceRegularSafeEditFormModel.toString());
            throw new DeviceException(ResultEnum.DEVICE_REGULAR_SAFE_NOT_EXIST);
        }
    }
}
