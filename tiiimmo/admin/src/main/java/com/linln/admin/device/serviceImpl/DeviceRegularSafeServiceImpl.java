package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.repository.DeviceRegularSafeRepository;
import com.linln.admin.device.serviceInter.DeviceRegularSafeServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceRegularSafeServiceImpl implements DeviceRegularSafeServiceInter {
    private DeviceRegularSafeRepository deviceRegularSafeRepository;

    @Autowired
    public void setDeviceRegularSafeRepository(DeviceRegularSafeRepository deviceRegularSafeRepository){
        this.deviceRegularSafeRepository = deviceRegularSafeRepository;
    }
}
