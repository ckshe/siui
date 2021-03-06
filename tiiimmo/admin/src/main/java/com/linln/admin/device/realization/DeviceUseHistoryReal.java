package com.linln.admin.device.realization;

import com.linln.admin.device.VO.DeviceUseHistoryVO;
import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.entity.DeviceUseHistory;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceUseHistoryEditForm;
import com.linln.admin.device.form.DeviceUseHistoryForm;
import com.linln.admin.device.resultVO.DeviceUseHistoryListResultVO;
import com.linln.admin.device.serviceImpl.DeviceUseHistoryServiceImpl;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DeviceUseHistoryReal {
    private DeviceUseHistoryServiceImpl deviceUseHistoryService;
    private UserServiceImpl userService;

    @Autowired
    public void setDeviceUseHistoryService(DeviceUseHistoryServiceImpl deviceUseHistoryService) {
        this.deviceUseHistoryService = deviceUseHistoryService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public DeviceUseHistoryVO getDeviceUseHistory(DeviceUseHistoryForm deviceUseHistoryForm) {
        DeviceUseHistoryVO deviceUseHistoryVO = new DeviceUseHistoryVO();
        List<DeviceUseHistory> deviceUseHistories = deviceUseHistoryService.findByPcbTaskCodeAndDeviceCode(deviceUseHistoryForm.getPcbTaskCode(), deviceUseHistoryForm.getDeviceCode());
        if (deviceUseHistories.size() == 0) {
            DeviceUseHistory deviceUseHistory = new DeviceUseHistory();
            BeanUtils.copyProperties(deviceUseHistoryForm, deviceUseHistory);
            deviceUseHistories.add(deviceUseHistory);
            deviceUseHistoryService.saveDeviceUseHistory(deviceUseHistory);
        }
        BeanUtils.copyProperties(deviceUseHistories.get(0), deviceUseHistoryVO);
        return deviceUseHistoryVO;
    }

    @Transactional
    public void editDeviceUseHistory(DeviceUseHistoryEditForm deviceUseHistoryEditForm) {
        DeviceUseHistory deviceUseHistory = deviceUseHistoryService.findById(deviceUseHistoryEditForm.getDeviceHistoryId());
        List<User> users = userService.findAll();
        if (deviceUseHistory == null) {
            log.error("【编辑设备使用记录】该设备使用记录不存在，deviceUseHistoryEditForm={}", deviceUseHistoryEditForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_SAFE_NOT_EXIST);
        }
        List<User> userList = users.stream().filter(e -> deviceUseHistoryEditForm.getUsePerson()!=null && deviceUseHistoryEditForm.getUsePerson().equalsIgnoreCase(e.getCardSequence())).collect(Collectors.toList());
        if (userList.size() != 0) {
            deviceUseHistoryEditForm.setUsePerson(userList.get(0).getNickname());
        }
        BeanUtils.copyProperties(deviceUseHistoryEditForm, deviceUseHistory);
        deviceUseHistoryService.saveDeviceUseHistory(deviceUseHistory);
    }

    public DeviceUseHistoryListResultVO getDeviceUseHistoryList(Example<DeviceUseHistory> deviceUseHistoryExample, Pageable pageable) {
        DeviceUseHistoryListResultVO deviceUseHistoryListResultVO = new DeviceUseHistoryListResultVO();
        List<DeviceUseHistoryVO> deviceUseHistoryVOS = new ArrayList<>();
        Page<DeviceUseHistory> deviceUseHistoryPage = deviceUseHistoryService.getDeviceUseHistoryList(deviceUseHistoryExample, pageable);
        for (DeviceUseHistory deviceUseHistory : deviceUseHistoryPage.getContent()) {
            DeviceUseHistoryVO deviceUseHistoryVO = new DeviceUseHistoryVO();
            BeanUtils.copyProperties(deviceUseHistory, deviceUseHistoryVO);
            deviceUseHistoryVOS.add(deviceUseHistoryVO);
        }
        deviceUseHistoryListResultVO.setTotal(deviceUseHistoryPage.getTotalElements());
        deviceUseHistoryListResultVO.setDeviceUseHistoryVOS(deviceUseHistoryVOS);
        return deviceUseHistoryListResultVO;
    }
}
