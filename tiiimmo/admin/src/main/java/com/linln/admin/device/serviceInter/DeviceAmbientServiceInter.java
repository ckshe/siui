package com.linln.admin.device.serviceInter;

import com.linln.admin.device.entity.DeviceAmbient;
import com.linln.admin.device.entity.DeviceDateSafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface DeviceAmbientServiceInter {
    void saveDeviceAmbient(DeviceAmbient deviceAmbient);
    DeviceAmbient findById(Integer Id);
    void deleteDeviceAmbient(DeviceAmbient deviceAmbient);
    Page<DeviceAmbient> getDeviceAmbientList(Specification<DeviceAmbient> Specification, Pageable pageable);
}
