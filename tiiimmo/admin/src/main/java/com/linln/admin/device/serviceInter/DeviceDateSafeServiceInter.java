package com.linln.admin.device.serviceInter;

import com.linln.admin.device.VO.DeviceDateSafeVO;
import com.linln.admin.device.entity.DeviceDateSafe;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface DeviceDateSafeServiceInter {
    Page<DeviceDateSafe> getDeviceDateSafes(Specification<DeviceDateSafe> Specification, Pageable pageable);
    List<DeviceDateSafe> findBySafeTimeAndSafeDeviceCode(Date safeTime, String safeDeviceCode);
    void saveAllDeviceDateSafe(List<DeviceDateSafe> deviceDateSafes);
    List<DeviceDateSafe> findDeviceDateSafeByIdList(List<Integer> idList);
}
