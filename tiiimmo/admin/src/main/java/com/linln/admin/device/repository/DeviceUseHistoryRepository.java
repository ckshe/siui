package com.linln.admin.device.repository;

import com.linln.admin.device.entity.DeviceUseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceUseHistoryRepository extends JpaRepository<DeviceUseHistory, Integer> {
    List<DeviceUseHistory> findByPcbTaskCodeAndDeviceCode(String pcbTaskCode, String deviceCode);
}
