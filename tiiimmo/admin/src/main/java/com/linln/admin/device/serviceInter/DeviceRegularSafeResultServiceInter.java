package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceRegularSafeResult;

import java.util.Date;
import java.util.List;

public interface DeviceRegularSafeResultServiceInter {
    List<DeviceRegularSafeResult> findByTheSafeTimeAndDeviceCode(Date theSafeDate, String deviceCode);
    void saveDeviceRegularSafeResults(List<DeviceRegularSafeResult> deviceRegularSafeResults);
}
