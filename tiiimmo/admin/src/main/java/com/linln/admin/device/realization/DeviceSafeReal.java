package com.linln.admin.device.realization;

import com.linln.admin.device.VO.DeviceSafeVO;
import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceSafeForm;
import com.linln.admin.device.resultVO.DeviceSafeResultVO;
import com.linln.admin.device.serviceImpl.DeviceSafeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DeviceSafeReal {
    private DeviceSafeServiceImpl deviceSafeService;

    @Autowired
    public void setDeviceSafeService(DeviceSafeServiceImpl deviceSafeService) {
        this.deviceSafeService = deviceSafeService;
    }

    @Transactional
    public void createDeviceSafe(DeviceSafeForm deviceSafeForm) {
        List<DeviceSafe> deviceSafes = deviceSafeService.findBySafeContentAndSafeDeviceCodeAndSafeType(deviceSafeForm.getSafeContent(), deviceSafeForm.getSafeDeviceCode(), deviceSafeForm.getSafeType());
        if (deviceSafes.size() != 0) {
            log.error("【创建设备维护内容】该设备维护内容已存在，deviceSafeForm={}", deviceSafeForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_SAFE_IS_EXIST);
        }
        DeviceSafe deviceSafe = new DeviceSafe();
        BeanUtils.copyProperties(deviceSafeForm, deviceSafe);
        deviceSafeService.createDeviceSafe(deviceSafe);
    }

    public DeviceSafeResultVO getDeviceSafes(Example<DeviceSafe> deviceSafeExample, Pageable pageable) {
        DeviceSafeResultVO deviceSafeResultVO = new DeviceSafeResultVO();
        List<DeviceSafeVO> deviceSafeVOS = new ArrayList<>();
        Page<DeviceSafe> deviceSafePage = deviceSafeService.getDeviceSafes(deviceSafeExample, pageable);
        for (DeviceSafe deviceSafe : deviceSafePage.getContent()) {
            DeviceSafeVO deviceSafeVO = new DeviceSafeVO();
            BeanUtils.copyProperties(deviceSafe, deviceSafeVO);
            deviceSafeVOS.add(deviceSafeVO);
        }
        deviceSafeResultVO.setTotal(deviceSafePage.getTotalElements());
        deviceSafeResultVO.setDeviceSafeVOS(deviceSafeVOS);
        return deviceSafeResultVO;
    }

    @Transactional
    public void editDeviceSafe(DeviceSafeForm deviceSafeForm) {
        DeviceSafe deviceSafe = deviceSafeService.findById(deviceSafeForm.getSafeId());
        if (deviceSafe == null) {
            log.error("【编辑设备维护内容】该设备维护内容不存在，deviceSafeForm={}", deviceSafeForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_SAFE_NOT_EXIST);
        }
        List<DeviceSafe> deviceSafes = deviceSafeService.findBySafeContentAndSafeDeviceCodeAndSafeType(deviceSafeForm.getSafeContent(), deviceSafeForm.getSafeDeviceCode(), deviceSafeForm.getSafeType());
        if (deviceSafes.size() != 0) {
            log.error("【编辑设备维护内容】该设备修改的内容已存在，deviceSafeForm={}", deviceSafeForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_SAFE_EDIT_IS_EXIST);
        }
        BeanUtils.copyProperties(deviceSafeForm, deviceSafe);
        deviceSafeService.saveDeviceSafe(deviceSafe);
    }

    @Transactional
    public void deleteDeviceSafe(Integer id) {
        DeviceSafe deviceSafe = deviceSafeService.findById(id);
        if (deviceSafe == null) {
            log.error("【删除设备维护内容】该设备维护内容不存在，id={}", id);
            throw new DeviceException(ResultEnum.DEVICE_SAFE_NOT_EXIST);
        }
        deviceSafeService.deleteDeviceSafe(deviceSafe);
    }
}
