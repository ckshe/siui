package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceDateSafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface DeviceDateSafeRepository extends JpaRepository<DeviceDateSafe, Integer>, JpaSpecificationExecutor<DeviceDateSafe> {
    List<DeviceDateSafe> findBySafeTimeAndSafeDeviceCode(Date safeTime, String safeDeviceCode);
    List<DeviceDateSafe> findByDateSafeIdIn(List<Integer> idList);
}
