package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.DeviceSafeResult;
import com.linln.admin.device.repository.DeviceSafeResultRepository;
import com.linln.admin.device.serviceInter.DeviceDateSafeServiceInter;
import com.linln.admin.device.serviceInter.DeviceSafeResultServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceSafeResultServiceImpl implements DeviceSafeResultServiceInter {
    private DeviceSafeResultRepository deviceSafeResultRepository;

    @Autowired
    public void setDeviceSafeResultRepository(DeviceSafeResultRepository deviceSafeResultRepository){
        this.deviceSafeResultRepository = deviceSafeResultRepository;
    }

    @Override
    public List<DeviceSafeResult> findAllDeviceSafeResult() {
        return deviceSafeResultRepository.findAll();
    }
}
