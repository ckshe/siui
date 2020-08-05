package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.VO.DeviceSafeVO;
import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.form.DeviceSafeForm;
import com.linln.admin.device.repository.DeviceDateSafeRepository;
import com.linln.admin.device.repository.DeviceSafeRepository;
import com.linln.admin.device.serviceInter.DeviceSafeServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<DeviceSafeVO> getDeviceSafes(Example<DeviceSafe> deviceSafeExample) {
        List<DeviceSafeVO> deviceSafeVOS = new ArrayList<>();
        List<DeviceSafe> deviceSafes = deviceSafeRepository.findAll(deviceSafeExample);
        for (DeviceSafe deviceSafe : deviceSafes){
            DeviceSafeVO deviceSafeVO = new DeviceSafeVO();
            BeanUtils.copyProperties(deviceSafe, deviceSafeVO);
            deviceSafeVOS.add(deviceSafeVO);
        }
        return deviceSafeVOS;
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
