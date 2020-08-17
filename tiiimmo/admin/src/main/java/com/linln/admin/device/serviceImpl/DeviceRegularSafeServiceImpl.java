package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.DeviceRegularSafe;
import com.linln.admin.device.repository.DeviceRegularSafeRepository;
import com.linln.admin.device.serviceInter.DeviceRegularSafeServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DeviceRegularSafeServiceImpl implements DeviceRegularSafeServiceInter {
    private DeviceRegularSafeRepository deviceRegularSafeRepository;

    @Autowired
    public void setDeviceRegularSafeRepository(DeviceRegularSafeRepository deviceRegularSafeRepository){
        this.deviceRegularSafeRepository = deviceRegularSafeRepository;
    }


    @Override
    public List<DeviceRegularSafe> findByThisSafeTime(Date thisSafeTime) {
        return deviceRegularSafeRepository.findByThisSafeTime(thisSafeTime);
    }

    @Override
    public void saveDeviceRegularSafe(DeviceRegularSafe deviceRegularSafe) {
        deviceRegularSafeRepository.save(deviceRegularSafe);
    }

    @Override
    public DeviceRegularSafe findById(Integer id) {
        return deviceRegularSafeRepository.findById(id).orElse(null);
    }

    @Override
    public Page<DeviceRegularSafe> getDeviceRegularSafes(Specification<DeviceRegularSafe> Specification, Pageable pageable) {
        return deviceRegularSafeRepository.findAll(Specification, pageable);
    }
}
