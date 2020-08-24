package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.entity.DeviceUseHistory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceUseHistoryServiceInter {
    List<DeviceUseHistory> findByPcbTaskCodeAndDeviceCode(String pcbTaskCode, String deviceCode);
    void saveDeviceUseHistory(DeviceUseHistory deviceUseHistory);
    DeviceUseHistory findById(Integer Id);
    Page<DeviceUseHistory> getDeviceUseHistoryList(Example<DeviceUseHistory> deviceUseHistoryExample, Pageable pageable);
}
