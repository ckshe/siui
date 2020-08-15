package com.linln.admin.device.realization;

import com.linln.admin.device.VO.DeviceDateSafeVO;
import com.linln.admin.device.entity.DeviceDateSafe;
import com.linln.admin.device.entity.DeviceSafe;
import com.linln.admin.device.entity.DeviceSafeResult;
import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.exception.DeviceException;
import com.linln.admin.device.form.DeviceDateSafeEditForm;
import com.linln.admin.device.form.DeviceDateSafeForm;
import com.linln.admin.device.formModel.DeviceDateSafeEditFormModel;
import com.linln.admin.device.resultVO.DeviceDateSafeListResultVO;
import com.linln.admin.device.resultVO.DeviceDateSafeResultVO;
import com.linln.admin.device.serviceImpl.DeviceDateSafeServiceImpl;
import com.linln.admin.device.serviceImpl.DeviceSafeResultServiceImpl;
import com.linln.admin.device.serviceImpl.DeviceSafeServiceImpl;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceDateSafeReal {
    private DeviceDateSafeServiceImpl deviceDateSafeService;
    private DeviceSafeServiceImpl deviceSafeService;
    private DeviceSafeResultServiceImpl deviceSafeResultService;
    private UserServiceImpl userService;

    @Autowired
    public void setDeviceDateSafeService(DeviceDateSafeServiceImpl deviceDateSafeService) {
        this.deviceDateSafeService = deviceDateSafeService;
    }

    @Autowired
    public void setDeviceSafeService(DeviceSafeServiceImpl deviceSafeService) {
        this.deviceSafeService = deviceSafeService;
    }

    @Autowired
    public void setDeviceSafeResultService(DeviceSafeResultServiceImpl deviceSafeResultService) {
        this.deviceSafeResultService = deviceSafeResultService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public DeviceDateSafeListResultVO getDeviceDateSafes(Specification<DeviceDateSafe> Specification, Pageable pageable) {
        DeviceDateSafeListResultVO deviceDateSafeListResultVO = new DeviceDateSafeListResultVO();
        List<DeviceDateSafeVO> deviceDateSafeVOS = new ArrayList<>();
        Page<DeviceDateSafe> deviceDateSafePage = deviceDateSafeService.getDeviceDateSafes(Specification, pageable);
        for (DeviceDateSafe deviceDateSafe : deviceDateSafePage.getContent()) {
            DeviceDateSafeVO deviceDateSafeVO = new DeviceDateSafeVO();
            BeanUtils.copyProperties(deviceDateSafe, deviceDateSafeVO);
            deviceDateSafeVOS.add(deviceDateSafeVO);
        }
        deviceDateSafeListResultVO.setTotal(deviceDateSafePage.getTotalElements());
        deviceDateSafeListResultVO.setDeviceDateSafeVOS(deviceDateSafeVOS);
        return deviceDateSafeListResultVO;
    }

    @Transactional
    public DeviceDateSafeResultVO getDeviceDateSafesByDate(DeviceDateSafeForm deviceDateSafeForm) {
        DeviceDateSafeResultVO deviceDateSafeResultVO = new DeviceDateSafeResultVO();
        List<DeviceDateSafeVO> deviceDateSafeVOS = new ArrayList<>();
        List<DeviceDateSafe> deviceDateSafes = deviceDateSafeService.findBySafeTimeAndSafeDeviceCode(deviceDateSafeForm.getSafeTime(), deviceDateSafeForm.getSafeDeviceCode());
        if (deviceDateSafes.size() == 0) {
            List<DeviceSafe> deviceSafes = deviceSafeService.findBySafeDeviceCode(deviceDateSafeForm.getSafeDeviceCode());
            for (DeviceSafe deviceSafe : deviceSafes) {
                DeviceDateSafe deviceDateSafe = new DeviceDateSafe();
                BeanUtils.copyProperties(deviceSafe, deviceDateSafe);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = deviceDateSafeForm.getSafeTime();
                String dateStr = format.format(date);
                try {
                    date = format.parse(dateStr);
                } catch (ParseException e) {
                    log.error("【生成日常维护记录】日期转换异常 SafeTime={}", date);
                    e.printStackTrace();
                    throw new DeviceException(ResultEnum.DATE_FORMAT_ERROR);
                }
                deviceDateSafe.setSafeTime(date);
                deviceDateSafes.add(deviceDateSafe);
            }
            deviceDateSafeService.saveAllDeviceDateSafe(deviceDateSafes);
        }
        List<String> safeTypeList = deviceDateSafes.stream().map(DeviceDateSafe::getSafeType).distinct().collect(Collectors.toList());
        deviceDateSafeResultVO.setDeviceSafeTypeS(safeTypeList);
        if (deviceDateSafeForm.getSafeType() != null && !"".equals(deviceDateSafeForm.getSafeType())) {
            deviceDateSafes = deviceDateSafes.stream().filter(e -> e.getSafeType().equals(deviceDateSafeForm.getSafeType())).collect(Collectors.toList());
        }
        for (DeviceDateSafe deviceDateSafe : deviceDateSafes) {
            DeviceDateSafeVO deviceDateSafeVO = new DeviceDateSafeVO();
            BeanUtils.copyProperties(deviceDateSafe, deviceDateSafeVO);
            deviceDateSafeVOS.add(deviceDateSafeVO);
        }
        deviceDateSafeResultVO.setDeviceDateSafeVOS(deviceDateSafeVOS);
        List<DeviceSafeResult> deviceSafeResults = deviceSafeResultService.findAllDeviceSafeResult();
        deviceSafeResults.sort(Comparator.comparing(DeviceSafeResult::getDefaultResult).reversed());
        List<String> safeResultList = deviceSafeResults.stream().map(DeviceSafeResult::getSafeResult).collect(Collectors.toList());
        deviceDateSafeResultVO.setDeviceSafeResultS(safeResultList);
        return deviceDateSafeResultVO;
    }

    @Transactional
    public void editDeviceDateSafes(DeviceDateSafeEditFormModel deviceDateSafeEditFormModel) {
        List<Integer> idList = deviceDateSafeEditFormModel.getDeviceDateSafeEditForms().stream().map(DeviceDateSafeEditForm::getDateSafeId).collect(Collectors.toList());
        List<DeviceDateSafe> deviceDateSafes = deviceDateSafeService.findDeviceDateSafeByIdList(idList);
        List<User> users = userService.findAll();
        for (int i = 0; i < deviceDateSafes.size(); i++) {
            int index = i;
            List<DeviceDateSafeEditForm> deviceDateSafeEditForms = deviceDateSafeEditFormModel.getDeviceDateSafeEditForms().stream().filter(e -> e.getDateSafeId().equals(deviceDateSafes.get(index).getDateSafeId())).collect(Collectors.toList());
            if (deviceDateSafeEditForms.size() != 0) {
                DeviceDateSafeEditForm deviceDateSafeEditForm = deviceDateSafeEditForms.get(0);
                deviceDateSafes.get(index).setSafeResult(deviceDateSafeEditForm.getSafeResult());
                List<User> userList = users.stream().filter(e -> deviceDateSafeEditForm.getSafePerson() != null && deviceDateSafeEditForm.getSafePerson().equalsIgnoreCase(e.getCardSequence())).collect(Collectors.toList());
                if (userList.size() != 0) {
                    deviceDateSafeEditForm.setSafePerson(userList.get(0).getNickname());
                }
                deviceDateSafes.get(index).setSafePerson(deviceDateSafeEditForm.getSafePerson());
            }
        }
        deviceDateSafeService.saveAllDeviceDateSafe(deviceDateSafes);
    }
}
