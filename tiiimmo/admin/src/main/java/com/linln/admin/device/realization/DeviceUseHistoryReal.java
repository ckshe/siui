package com.linln.admin.device.realization;

import com.linln.admin.device.VO.DeviceUseHistoryVO;
import com.linln.admin.device.entity.DeviceUseHistory;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceUseHistoryEditForm;
import com.linln.admin.device.form.DeviceUseHistoryForm;
import com.linln.admin.device.serviceImpl.DeviceUseHistoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeviceUseHistoryReal {
    private DeviceUseHistoryServiceImpl deviceUseHistoryService;

    @Autowired
    public void setDeviceUseHistoryService(DeviceUseHistoryServiceImpl deviceUseHistoryService){
        this.deviceUseHistoryService = deviceUseHistoryService;
    }

    public DeviceUseHistoryVO getDeviceUseHistory(DeviceUseHistoryForm deviceUseHistoryForm){
        DeviceUseHistoryVO deviceUseHistoryVO = new DeviceUseHistoryVO();
        List<DeviceUseHistory> deviceUseHistories = deviceUseHistoryService.findByPcbTaskCodeAndDeviceCode(deviceUseHistoryForm.getPcbTaskCode(), deviceUseHistoryForm.getDeviceCode());
        if (deviceUseHistories.size() == 0){
            DeviceUseHistory deviceUseHistory = new DeviceUseHistory();
            BeanUtils.copyProperties(deviceUseHistoryForm, deviceUseHistory);
            deviceUseHistories.add(deviceUseHistory);
            deviceUseHistoryService.saveDeviceUseHistory(deviceUseHistory);
        }
        BeanUtils.copyProperties(deviceUseHistories.get(0), deviceUseHistoryVO);
        return deviceUseHistoryVO;
    }

    public void editDeviceUseHistory(DeviceUseHistoryEditForm deviceUseHistoryEditForm){
        DeviceUseHistory deviceUseHistory = deviceUseHistoryService.findById(deviceUseHistoryEditForm.getDeviceHistoryId());
        if (deviceUseHistory == null){
            log.error("【编辑设备使用记录】该设备使用记录不存在，deviceUseHistoryEditForm={}", deviceUseHistoryEditForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_SAFE_NOT_EXIST);
        }

    }
}
