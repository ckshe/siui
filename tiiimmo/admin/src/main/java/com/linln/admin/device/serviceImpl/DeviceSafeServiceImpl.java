package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.repository.DeviceSafeRepository;
import com.linln.admin.device.serviceInter.DeviceSafeServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeviceSafeServiceImpl implements DeviceSafeServiceInter {
    private DeviceSafeRepository deviceSafeRepository;

    @Autowired
    public void setDeviceDateSafeRepository(DeviceSafeRepository deviceSafeRepository){
        this.deviceSafeRepository = deviceSafeRepository;
    }

    @Override
    public void createDeviceSafe(DeviceSafe deviceSafe) {
        deviceSafeRepository.save(deviceSafe);
    }

    @Override
    public List<DeviceSafe> findBySafeContentAndSafeDeviceCodeAndSafeType(String safeContent, String SafeDeviceCode, String SafeType) {
        return deviceSafeRepository.findBySafeContentAndSafeDeviceCodeAndSafeType(safeContent, SafeDeviceCode, SafeType);
    }

    @Override
    public Page<DeviceSafe> getDeviceSafes(Example<DeviceSafe> deviceSafeExample, Pageable pageable) {
        return deviceSafeRepository.findAll(deviceSafeExample, pageable);
    }

    @Override
    public DeviceSafe findById(Integer id) {
        return deviceSafeRepository.findById(id).orElse(null);
    }

    @Override
    public void saveDeviceSafe(DeviceSafe deviceSafe) {
        deviceSafeRepository.save(deviceSafe);
    }

    @Override
    public void deleteDeviceSafe(DeviceSafe deviceSafe) {
        deviceSafeRepository.delete(deviceSafe);
    }

    @Override
    public List<DeviceSafe> findBySafeDeviceCode(String safeDeviceCode) {
        return deviceSafeRepository.findBySafeDeviceCode(safeDeviceCode);
    }
}
