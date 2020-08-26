package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceAmbient;

public interface DeviceAmbientServiceInter {
    void saveDeviceAmbient(DeviceAmbient deviceAmbient);
    DeviceAmbient findById(Integer Id);
    void deleteDeviceAmbient(DeviceAmbient deviceAmbient);
}
