package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.repository.DeviceRegularSafeContentRepository;
import com.linln.admin.device.serviceInter.DeviceRegularSafeContentServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceRegularSafeContentServiceImpl implements DeviceRegularSafeContentServiceInter {
    private DeviceRegularSafeContentRepository deviceRegularSafeContentRepository;

    @Autowired
    public void setDeviceRegularSafeContentRepository(DeviceRegularSafeContentRepository deviceRegularSafeContentRepository){
        this.deviceRegularSafeContentRepository = deviceRegularSafeContentRepository;
    }
}
