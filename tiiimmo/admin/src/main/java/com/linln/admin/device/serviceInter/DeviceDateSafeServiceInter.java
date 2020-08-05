package com.linln.admin.device.serviceInter;

import com.linln.admin.device.VO.DeviceDateSafeVO;
import com.linln.admin.device.entity.DeviceDateSafe;
import org.springframework.data.domain.Example;

import java.util.Date;
import java.util.List;

public interface DeviceDateSafeServiceInter {
    List<DeviceDateSafeVO> getDeviceDateSafes(Example<DeviceDateSafe> deviceDateSafeExample);
    List<DeviceDateSafe> findBySafeTimeAndSafeDeviceCode(Date safeTime, String safeDeviceCode);
    void saveAllDeviceDateSafe(List<DeviceDateSafe> deviceDateSafes);
    List<DeviceDateSafe> findDeviceDateSafeByIdList(List<Integer> idList);
}
