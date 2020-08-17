package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceRegularSafeResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DeviceRegularSafeResultRepository extends JpaRepository<DeviceRegularSafeResult, Integer> {
    List<DeviceRegularSafeResult> findByTheSafeTimeAndDeviceCode(Date theSafeDate, String deviceCode);
}
