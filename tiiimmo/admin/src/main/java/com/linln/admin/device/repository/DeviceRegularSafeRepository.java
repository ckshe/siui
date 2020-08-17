package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceRegularSafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DeviceRegularSafeRepository extends JpaRepository<DeviceRegularSafe, Integer> {
    List<DeviceRegularSafe> findByThisSafeTimeAndDeviceCode(Date thisSafeTime, String deviceCode);
    Page<DeviceRegularSafe> findByDeviceCodeOrderByThisSafeTimeDesc(String deviceCode, Pageable pageable);
}
