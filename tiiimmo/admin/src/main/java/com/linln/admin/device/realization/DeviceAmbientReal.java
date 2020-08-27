package com.linln.admin.device.realization;

import com.linln.admin.device.VO.DeviceAmbientVO;
import com.linln.admin.device.entity.DeviceAmbient;
import com.linln.admin.device.entity.DeviceDateSafe;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceAmbientForm;
import com.linln.admin.device.resultVO.DeviceAmbientListResultVO;
import com.linln.admin.device.resultVO.DeviceUseHistoryListResultVO;
import com.linln.admin.device.serviceImpl.DeviceAmbientServiceImpl;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceAmbientReal {
    private DeviceAmbientServiceImpl deviceAmbientService;
    private UserServiceImpl userService;

    @Autowired
    public void setDeviceAmbientService(DeviceAmbientServiceImpl deviceAmbientService){
        this.deviceAmbientService = deviceAmbientService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService){
        this.userService = userService;
    }

    @Transactional
    public void createDeviceAmbient(DeviceAmbientForm deviceAmbientForm){
        DeviceAmbient deviceAmbient = new DeviceAmbient();
        List<User> users = userService.findAll();
        List<User> userList = users.stream().filter(e -> deviceAmbientForm.getAmbientRecordPerson()!=null && deviceAmbientForm.getAmbientRecordPerson().equalsIgnoreCase(e.getCardSequence())).collect(Collectors.toList());
        if (userList.size() != 0) {
            deviceAmbientForm.setAmbientRecordPerson(userList.get(0).getNickname());
        }
        BeanUtils.copyProperties(deviceAmbientForm, deviceAmbient);
        deviceAmbientService.saveDeviceAmbient(deviceAmbient);
    }

    @Transactional
    public void editDeviceAmbient(DeviceAmbientForm deviceAmbientForm){
        DeviceAmbient deviceAmbient = deviceAmbientService.findById(deviceAmbientForm.getAmbientId());
        if (deviceAmbient == null){
            log.error("【编辑车间环境记录】该环境记录不存在，deviceAmbientForm={}", deviceAmbientForm.toString());
            throw new DeviceException(ResultEnum.DEVICE_AMBIENT_NOT_EXIST);
        }
        List<User> users = userService.findAll();
        List<User> userList = users.stream().filter(e -> deviceAmbientForm.getAmbientRecordPerson()!=null && deviceAmbientForm.getAmbientRecordPerson().equalsIgnoreCase(e.getCardSequence())).collect(Collectors.toList());
        if (userList.size() != 0) {
            deviceAmbientForm.setAmbientRecordPerson(userList.get(0).getNickname());
        }
        BeanUtils.copyProperties(deviceAmbientForm, deviceAmbient);
        deviceAmbientService.saveDeviceAmbient(deviceAmbient);
    }

    @Transactional
    public void deleteDeviceAmbient(Integer id){
        DeviceAmbient deviceAmbient = deviceAmbientService.findById(id);
        if (deviceAmbient == null){
            log.error("【删除车间环境记录】该环境记录不存在，id={}", id);
            throw new DeviceException(ResultEnum.DEVICE_AMBIENT_NOT_EXIST);
        }
        deviceAmbientService.deleteDeviceAmbient(deviceAmbient);
    }

    public DeviceAmbientListResultVO getDeviceAmbientList(Specification<DeviceAmbient> Specification, Pageable pageable){
        DeviceAmbientListResultVO deviceAmbientListResultVO = new DeviceAmbientListResultVO();
        List<DeviceAmbientVO> deviceAmbientVOS = new ArrayList<>();
        Page<DeviceAmbient> deviceAmbientPage = deviceAmbientService.getDeviceAmbientList(Specification, pageable);
        for (DeviceAmbient deviceAmbient : deviceAmbientPage.getContent()){
            DeviceAmbientVO deviceAmbientVO = new DeviceAmbientVO();
            BeanUtils.copyProperties(deviceAmbient, deviceAmbientVO);
            deviceAmbientVOS.add(deviceAmbientVO);
        }
        deviceAmbientListResultVO.setTotal(deviceAmbientPage.getTotalElements());
        deviceAmbientListResultVO.setDeviceAmbientVOList(deviceAmbientVOS);
        return deviceAmbientListResultVO;
    }
}
