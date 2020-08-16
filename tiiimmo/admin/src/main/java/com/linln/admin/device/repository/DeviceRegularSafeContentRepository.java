package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceRegularSafeContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRegularSafeContentRepository extends JpaRepository<DeviceRegularSafeContent, Integer> {
    List<DeviceRegularSafeContent> findByDeviceCodeAndSafeContent(String deviceCode, String deviceRegularSafeContent);
    List<DeviceRegularSafeContent> findByDeviceCode(String deviceCode);
}
