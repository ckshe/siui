package com.linln.admin.device.controller;

import com.linln.admin.device.realization.DeviceRegularSafeContentReal;
import com.linln.admin.device.resultVO.ResultVO;
import com.linln.admin.device.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/deviceRegularSafeContent")
@Slf4j
public class DeviceRegularSafeContentController {
    private DeviceRegularSafeContentReal deviceRegularSafeContentReal;

    @Autowired
    public void setDeviceRegularSafeContentReal(DeviceRegularSafeContentReal deviceRegularSafeContentReal){
        this.deviceRegularSafeContentReal = deviceRegularSafeContentReal;
    }

    @RequestMapping("/create")
    public ResultVO<Object> createDeviceRegularSafeContent() {
        return ResultVOUtil.success(null);
    }
}
