package com.linln.admin.device.realization;

import com.linln.admin.base.domain.Device;
import com.linln.admin.base.service.impl.DeviceServiceImpl;
import com.linln.admin.device.VO.DeviceRegularSafeContentVO;
import com.linln.admin.device.entity.DeviceRegularSafeContent;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceRegularSafeContentForm;
import com.linln.admin.device.resultVO.DeviceRegularSafeContentListResultVO;
import com.linln.admin.device.serviceImpl.DeviceRegularSafeContentServiceImpl;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceRegularSafeContentReal {
    private DeviceRegularSafeContentServiceImpl deviceRegularSafeContentService;
    private DeviceServiceImpl deviceService;

    @Autowired
    public void setDeviceRegularSafeContentService(DeviceRegularSafeContentServiceImpl deviceRegularSafeContentService){
        this.deviceRegularSafeContentService = deviceRegularSafeContentService;
    }

    @Autowired
    public void setDeviceService(DeviceServiceImpl deviceService){
        this.deviceService = deviceService;
    }

    @Transactional
    public void createDeviceRegularSafeContent(DeviceRegularSafeContentForm deviceRegularSafeContentForm){
        List<DeviceRegularSafeContent> deviceRegularSafeContents = deviceRegularSafeContentService.findByDeviceCodeAndDeviceRegularSafeContent(deviceRegularSafeContentForm.getDeviceCode(), deviceRegularSafeContentForm.getDeviceRegularSafeContent());
        if (deviceRegularSafeContents.size() != 0){
            log.error("【创建定期检测内容】该定期检测内容已存在，deviceRegularSafeContentForm={}", deviceRegularSafeContentForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_REGULAR_SAFE_CONTENT_IS_EXIST);
        }
        List<Device> devices = deviceService.list();
        devices = devices.stream().filter(e->e.getDevice_code().equals(deviceRegularSafeContentForm.getDeviceCode())).collect(Collectors.toList());
        if (devices.size() == 0){
            log.error("【创建定期检测内容】设备不存在，deviceRegularSafeContentForm={}", deviceRegularSafeContentForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_NOT_EXIST);
        }
        DeviceRegularSafeContent deviceRegularSafeContent = new DeviceRegularSafeContent();
        BeanUtils.copyProperties(deviceRegularSafeContentForm, deviceRegularSafeContent);
        deviceRegularSafeContent.setDeviceName(devices.get(0).getDevice_name());
        deviceRegularSafeContentService.createDeviceRegularSafeContent(deviceRegularSafeContent);
    }

    public DeviceRegularSafeContentListResultVO getDeviceRegularSafeContents(Example<DeviceRegularSafeContent> deviceRegularSafeContentExample, Pageable pageable){
        DeviceRegularSafeContentListResultVO deviceRegularSafeContentListResultVO = new DeviceRegularSafeContentListResultVO();
        List<DeviceRegularSafeContentVO> deviceRegularSafeContentVOS = new ArrayList<>();
        Page<DeviceRegularSafeContent> deviceRegularSafeContentPage = deviceRegularSafeContentService.getDeviceRegularSafeContents(deviceRegularSafeContentExample, pageable);
        for (DeviceRegularSafeContent deviceRegularSafeContent : deviceRegularSafeContentPage.getContent()) {
            DeviceRegularSafeContentVO deviceRegularSafeContentVO = new DeviceRegularSafeContentVO();
            BeanUtils.copyProperties(deviceRegularSafeContent, deviceRegularSafeContentVO);
            deviceRegularSafeContentVOS.add(deviceRegularSafeContentVO);
        }
        deviceRegularSafeContentListResultVO.setTotal(deviceRegularSafeContentPage.getTotalElements());
        deviceRegularSafeContentListResultVO.setDeviceRegularSafeContentVOList(deviceRegularSafeContentVOS);
        return deviceRegularSafeContentListResultVO;
    }

    @Transactional
    public void editDeviceRegularSafeContent(DeviceRegularSafeContentForm deviceRegularSafeContentForm){
        DeviceRegularSafeContent deviceRegularSafeContent = deviceRegularSafeContentService.findById(deviceRegularSafeContentForm.getRegularSafeContentId());
        if (deviceRegularSafeContent == null){
            log.error("【编辑定期检测内容】该定期检测内容不存在，deviceRegularSafeContentForm={}", deviceRegularSafeContentForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_REGULAR_SAFE_CONTENT_NOT_EXIST);
        }
        List<DeviceRegularSafeContent> deviceRegularSafeContents = deviceRegularSafeContentService.findByDeviceCodeAndDeviceRegularSafeContent(deviceRegularSafeContentForm.getDeviceCode(), deviceRegularSafeContentForm.getDeviceRegularSafeContent());
        if (deviceRegularSafeContents.size() != 0){
            log.error("【编辑定期检测内容】该定期检测内容已存在，deviceRegularSafeContentForm={}", deviceRegularSafeContentForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_REGULAR_SAFE_CONTENT_IS_EXIST);
        }
        List<Device> devices = deviceService.list();
        devices = devices.stream().filter(e->e.getDevice_code().equals(deviceRegularSafeContentForm.getDeviceCode())).collect(Collectors.toList());
        if (devices.size() == 0){
            log.error("【编辑定期检测内容】设备不存在，deviceRegularSafeContentForm={}", deviceRegularSafeContentForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_NOT_EXIST);
        }
        BeanUtils.copyProperties(deviceRegularSafeContentForm, deviceRegularSafeContent);
        deviceRegularSafeContent.setDeviceName(devices.get(0).getDevice_name());
        deviceRegularSafeContentService.saveDeviceRegularSafeContent(deviceRegularSafeContent);
    }

    @Transactional
    public void deleteDeviceRegularSafeContent(Integer id){
        DeviceRegularSafeContent deviceRegularSafeContent = deviceRegularSafeContentService.findById(id);
        if (deviceRegularSafeContent == null){
            log.error("【删除定期检测内容】该定期检测内容不存在，id={}", id);
            throw new DeviceException(ResultEnum.DEVICE_REGULAR_SAFE_CONTENT_NOT_EXIST);
        }
        deviceRegularSafeContentService.deleteDeviceRegularSafeContent(deviceRegularSafeContent);
    }
}
