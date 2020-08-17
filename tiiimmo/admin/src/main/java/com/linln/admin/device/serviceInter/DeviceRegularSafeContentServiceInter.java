package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceRegularSafeContent;
import com.linln.admin.device.entity.DeviceSafe;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceRegularSafeContentServiceInter {
    List<DeviceRegularSafeContent> findByDeviceCodeAndDeviceRegularSafeContent(String deviceCode, String deviceRegularSafeContent);
    void createDeviceRegularSafeContent(DeviceRegularSafeContent deviceRegularSafeContent);
    Page<DeviceRegularSafeContent> getDeviceRegularSafeContents(Example<DeviceRegularSafeContent> deviceRegularSafeContentExample, Pageable pageable);
    DeviceRegularSafeContent findById(Integer id);
    void saveDeviceRegularSafeContent(DeviceRegularSafeContent deviceRegularSafeContent);
    void deleteDeviceRegularSafeContent(DeviceRegularSafeContent deviceRegularSafeContent);
    List<DeviceRegularSafeContent> findByDeviceCode(String deviceCode);
}
