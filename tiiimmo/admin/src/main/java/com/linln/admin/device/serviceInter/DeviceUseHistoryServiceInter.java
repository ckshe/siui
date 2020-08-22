package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceUseHistory;

import java.util.List;

public interface DeviceUseHistoryServiceInter {
    List<DeviceUseHistory> findByPcbTaskCodeAndDeviceCode(String pcbTaskCode, String deviceCode);
    void saveDeviceUseHistory(DeviceUseHistory deviceUseHistory);
}
