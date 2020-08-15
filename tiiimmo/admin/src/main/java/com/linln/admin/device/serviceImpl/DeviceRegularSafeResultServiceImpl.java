package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.repository.DeviceRegularSafeResultRepository;
import com.linln.admin.device.serviceInter.DeviceRegularSafeResultServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceRegularSafeResultServiceImpl implements DeviceRegularSafeResultServiceInter {
    private DeviceRegularSafeResultRepository deviceRegularSafeResultRepository;

    @Autowired
    public void setDeviceRegularSafeResultRepository(DeviceRegularSafeResultRepository deviceRegularSafeResultRepository){
        this.deviceRegularSafeResultRepository = deviceRegularSafeResultRepository;
    }
}
