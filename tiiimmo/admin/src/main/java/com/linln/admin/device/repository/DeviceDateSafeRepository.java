package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceDateSafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DeviceDateSafeRepository extends JpaRepository<DeviceDateSafe, Integer> {
    List<DeviceDateSafe> findBySafeTimeAndSafeDeviceCode(Date safeTime, String safeDeviceCode);
    List<DeviceDateSafe> findByIdIn(List<Integer> idList);
}
