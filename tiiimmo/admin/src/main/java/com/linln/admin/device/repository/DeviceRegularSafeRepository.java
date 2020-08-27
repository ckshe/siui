package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceRegularSafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface DeviceRegularSafeRepository extends JpaRepository<DeviceRegularSafe, Integer>, JpaSpecificationExecutor<DeviceRegularSafe> {
    List<DeviceRegularSafe> findByThisSafeTime(Date thisSafeTime);
    Page<DeviceRegularSafe> findByDeviceCodeOrderByThisSafeTimeDesc(String deviceCode, Pageable pageable);
}
