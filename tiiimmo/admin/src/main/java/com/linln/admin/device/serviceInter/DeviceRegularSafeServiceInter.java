package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceRegularSafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface DeviceRegularSafeServiceInter {
    List<DeviceRegularSafe> findByThisSafeTimeAndDeviceCode(Date thisSafeTime, String deviceCode);
    void saveDeviceRegularSafe(DeviceRegularSafe deviceRegularSafe);
    DeviceRegularSafe findById(Integer id);
    Page<DeviceRegularSafe> findByDeviceCodeOrderByThisSafeTimeDesc(String deviceCode, Pageable pageable);
}
