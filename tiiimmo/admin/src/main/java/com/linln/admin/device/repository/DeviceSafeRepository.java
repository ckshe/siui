package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceSafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceSafeRepository extends JpaRepository<DeviceSafe, Integer> {
    List<DeviceSafe> findBySafeContentAndSafeDeviceCodeAndSafeType(String safeContent, String SafeDeviceCode, String SafeType);
    List<DeviceSafe> findBySafeDeviceCode(String safeDeviceCode);
}
