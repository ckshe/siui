package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.DeviceUseHistory;
import com.linln.admin.device.repository.DeviceUseHistoryRepository;
import com.linln.admin.device.serviceInter.DeviceUseHistoryServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeviceUseHistoryServiceImpl implements DeviceUseHistoryServiceInter {
    private DeviceUseHistoryRepository deviceUseHistoryRepository;

    @Autowired
    public void setDeviceUseHistoryRepository(DeviceUseHistoryRepository deviceUseHistoryRepository){
        this.deviceUseHistoryRepository = deviceUseHistoryRepository;
    }

    @Override
    public List<DeviceUseHistory> findByPcbTaskCodeAndDeviceCode(String pcbTaskCode, String deviceCode) {
        return deviceUseHistoryRepository.findByPcbTaskCodeAndDeviceCode(pcbTaskCode, deviceCode);
    }

    @Override
    public void saveDeviceUseHistory(DeviceUseHistory deviceUseHistory) {
        deviceUseHistoryRepository.save(deviceUseHistory);
    }

    @Override
    public DeviceUseHistory findById(Integer Id) {
        return deviceUseHistoryRepository.findById(Id).orElse(null);
    }

    @Override
    public Page<DeviceUseHistory> getDeviceUseHistoryList(Example<DeviceUseHistory> deviceUseHistoryExample, Pageable pageable) {
        return deviceUseHistoryRepository.findAll(deviceUseHistoryExample, pageable);
    }
}
