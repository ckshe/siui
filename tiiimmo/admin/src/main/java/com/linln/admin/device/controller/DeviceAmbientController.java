package com.linln.admin.device.controller;

import com.linln.admin.device.realization.DeviceAmbientReal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/deviceAmbient")
@Slf4j
public class DeviceAmbientController {
    private DeviceAmbientReal deviceAmbientReal;

    @Autowired
    public void setDeviceAmbientReal(DeviceAmbientReal deviceAmbientReal){
        this.deviceAmbientReal = deviceAmbientReal;
    }


}
