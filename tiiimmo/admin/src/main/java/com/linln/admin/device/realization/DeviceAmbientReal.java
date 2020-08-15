package com.linln.admin.device.realization;

import com.linln.admin.device.serviceImpl.DeviceAmbientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceAmbientReal {
    private DeviceAmbientServiceImpl deviceAmbientService;

    @Autowired
    public void setDeviceAmbientService(DeviceAmbientServiceImpl deviceAmbientService){
        this.deviceAmbientService = deviceAmbientService;
    }
}
