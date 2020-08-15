package com.linln.admin.device.realization;

import com.linln.admin.device.serviceImpl.DeviceRegularSafeContentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceRegularSafeContentReal {
    private DeviceRegularSafeContentServiceImpl deviceRegularSafeContentService;

    @Autowired
    public void setDeviceRegularSafeContentService(DeviceRegularSafeContentServiceImpl deviceRegularSafeContentService){
        this.deviceRegularSafeContentService = deviceRegularSafeContentService;
    }
}
