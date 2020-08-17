package com.linln.admin.device.serviceImpl;

import com.linln.admin.device.VO.DeviceDateSafeVO;
import com.linln.admin.device.entity.DeviceDateSafe;
import com.linln.admin.device.repository.DeviceDateSafeRepository;
import com.linln.admin.device.serviceInter.DeviceDateSafeServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DeviceDateSafeServiceImpl implements DeviceDateSafeServiceInter {
    private DeviceDateSafeRepository deviceDateSafeRepository;

    @Autowired
    public void setDeviceDateSafeRepository(DeviceDateSafeRepository deviceDateSafeRepository){
        this.deviceDateSafeRepository = deviceDateSafeRepository;
    }

    @Override
    public Page<DeviceDateSafe> getDeviceDateSafes(Specification<DeviceDateSafe> Specification, Pageable pageable) {
        return deviceDateSafeRepository.findAll(Specification, pageable);
    }

    @Override
    public List<DeviceDateSafe> findBySafeTimeAndSafeDeviceCode(Date safeTime, String safeDeviceCode) {
        return deviceDateSafeRepository.findBySafeTimeAndSafeDeviceCode(safeTime, safeDeviceCode);
    }

    @Override
    public void saveAllDeviceDateSafe(List<DeviceDateSafe> deviceDateSafes) {
        deviceDateSafeRepository.saveAll(deviceDateSafes);
    }

    @Override
    public List<DeviceDateSafe> findDeviceDateSafeByIdList(List<Integer> idList) {
        return deviceDateSafeRepository.findByDateSafeIdIn(idList);
    }
}
