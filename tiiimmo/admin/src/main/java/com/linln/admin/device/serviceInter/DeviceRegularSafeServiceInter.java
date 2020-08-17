package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceRegularSafe;

import java.util.Date;
import java.util.List;

public interface DeviceRegularSafeServiceInter {
    List<DeviceRegularSafe> findByThisSafeTime(Date thisSafeTime);
    void saveDeviceRegularSafe(DeviceRegularSafe deviceRegularSafe);
    DeviceRegularSafe findById(Integer id);
}
