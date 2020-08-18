package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceRegularSafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface DeviceRegularSafeServiceInter {
    List<DeviceRegularSafe> findByThisSafeTime(Date thisSafeTime);
    void saveDeviceRegularSafe(DeviceRegularSafe deviceRegularSafe);
    DeviceRegularSafe findById(Integer id);
    Page<DeviceRegularSafe> getDeviceRegularSafes(Specification<DeviceRegularSafe> Specification, Pageable pageable);
}
