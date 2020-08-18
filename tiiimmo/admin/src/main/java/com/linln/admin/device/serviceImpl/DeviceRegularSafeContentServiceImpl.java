package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.DeviceRegularSafeContent;
import com.linln.admin.device.repository.DeviceRegularSafeContentRepository;
import com.linln.admin.device.serviceInter.DeviceRegularSafeContentServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeviceRegularSafeContentServiceImpl implements DeviceRegularSafeContentServiceInter {
    private DeviceRegularSafeContentRepository deviceRegularSafeContentRepository;

    @Autowired
    public void setDeviceRegularSafeContentRepository(DeviceRegularSafeContentRepository deviceRegularSafeContentRepository){
        this.deviceRegularSafeContentRepository = deviceRegularSafeContentRepository;
    }

    @Override
    public List<DeviceRegularSafeContent> findByDeviceCodeAndDeviceRegularSafeContent(String deviceCode, String deviceRegularSafeContent) {
        return deviceRegularSafeContentRepository.findByDeviceCodeAndSafeContent(deviceCode, deviceRegularSafeContent);
    }

    @Override
    public void createDeviceRegularSafeContent(DeviceRegularSafeContent deviceRegularSafeContent) {
        deviceRegularSafeContentRepository.save(deviceRegularSafeContent);
    }

    @Override
    public Page<DeviceRegularSafeContent> getDeviceRegularSafeContents(Example<DeviceRegularSafeContent> deviceRegularSafeContentExample, Pageable pageable) {
        return deviceRegularSafeContentRepository.findAll(deviceRegularSafeContentExample, pageable);
    }

    @Override
    public DeviceRegularSafeContent findById(Integer id) {
        return deviceRegularSafeContentRepository.findById(id).orElse(null);
    }

    @Override
    public void saveDeviceRegularSafeContent(DeviceRegularSafeContent deviceRegularSafeContent) {
        deviceRegularSafeContentRepository.save(deviceRegularSafeContent);
    }

    @Override
    public void deleteDeviceRegularSafeContent(DeviceRegularSafeContent deviceRegularSafeContent) {
        deviceRegularSafeContentRepository.delete(deviceRegularSafeContent);
    }

    @Override
    public List<DeviceRegularSafeContent> findByDeviceCode(String deviceCode) {
        return deviceRegularSafeContentRepository.findByDeviceCode(deviceCode);
    }
}
