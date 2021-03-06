package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceAmbient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeviceAmbientRepository extends JpaRepository<DeviceAmbient, Integer>, JpaSpecificationExecutor<DeviceAmbient> {
    Page<DeviceAmbient> findAllByOrderByAmbientRecordTimeDesc(Pageable pageable);
}
