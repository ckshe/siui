package com.linln.admin.device.serviceInter;

import com.linln.admin.device.VO.DeviceSafeVO;
import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.form.DeviceSafeForm;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceSafeServiceInter {
    void createDeviceSafe(DeviceSafe deviceSafe);
    List<DeviceSafe> findBySafeContentAndSafeDeviceCodeAndSafeType(String safeContent, String SafeDeviceCode, String SafeType);
    Page<DeviceSafe> getDeviceSafes(Example<DeviceSafe> deviceSafeExample, Pageable pageable);
    DeviceSafe findById(Integer id);
    void saveDeviceSafe(DeviceSafe deviceSafe);
    void deleteDeviceSafe(DeviceSafe deviceSafe);
    List<DeviceSafe> findBySafeDeviceCode(String safeDeviceCode);
}
