package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.entity.DeviceAmbient;
import com.linln.admin.device.entity.DeviceDateSafe;
import com.linln.admin.device.repository.DeviceAmbientRepository;
import com.linln.admin.device.serviceInter.DeviceAmbientServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceAmbientServiceImpl implements DeviceAmbientServiceInter {
   private DeviceAmbientRepository deviceAmbientRepository;

   @Autowired
   public void setDeviceAmbientRepository(DeviceAmbientRepository deviceAmbientRepository){
       this.deviceAmbientRepository = deviceAmbientRepository;
   }

    @Override
    public void saveDeviceAmbient(DeviceAmbient deviceAmbient) {
        deviceAmbientRepository.save(deviceAmbient);
    }

    @Override
    public DeviceAmbient findById(Integer Id) {
        return deviceAmbientRepository.findById(Id).orElse(null);
    }

    @Override
    public void deleteDeviceAmbient(DeviceAmbient deviceAmbient) {
        deviceAmbientRepository.delete(deviceAmbient);
    }

    @Override
    public Page<DeviceAmbient> getDeviceAmbientList(Specification<DeviceAmbient> Specification, Pageable pageable) {
        return deviceAmbientRepository.findAll(Specification, pageable);
    }
}
