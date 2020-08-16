package com.linln.admin.device.resultVO;

import com.linln.admin.device.VO.DeviceRegularSafeContentVO;
import lombok.Data;

import java.util.List;

@Data
public class DeviceRegularSafeContentListResultVO {
    private Long total;
    List<DeviceRegularSafeContentVO> deviceRegularSafeContentVOList;
}
