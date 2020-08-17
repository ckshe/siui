package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.DeviceRegularSafeResult;
import com.linln.admin.device.repository.DeviceRegularSafeResultRepository;
import com.linln.admin.device.serviceInter.DeviceRegularSafeResultServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DeviceRegularSafeResultServiceImpl implements DeviceRegularSafeResultServiceInter {
    private DeviceRegularSafeResultRepository deviceRegularSafeResultRepository;

    @Autowired
    public void setDeviceRegularSafeResultRepository(DeviceRegularSafeResultRepository deviceRegularSafeResultRepository){
        this.deviceRegularSafeResultRepository = deviceRegularSafeResultRepository;
    }

    @Override
    public List<DeviceRegularSafeResult> findByTheSafeTimeAndDeviceCode(Date theSafeDate, String deviceCode) {
        return deviceRegularSafeResultRepository.findByTheSafeTimeAndDeviceCode(theSafeDate, deviceCode);
    }

    @Override
    public void saveDeviceRegularSafeResults(List<DeviceRegularSafeResult> deviceRegularSafeResults) {
        deviceRegularSafeResultRepository.saveAll(deviceRegularSafeResults);
    }
}
