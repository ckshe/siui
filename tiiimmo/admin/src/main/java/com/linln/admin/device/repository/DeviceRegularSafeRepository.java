package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceRegularSafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DeviceRegularSafeRepository extends JpaRepository<DeviceRegularSafe, Integer> {
    List<DeviceRegularSafe> findByThisSafeTime(Date thisSafeTime);
}
