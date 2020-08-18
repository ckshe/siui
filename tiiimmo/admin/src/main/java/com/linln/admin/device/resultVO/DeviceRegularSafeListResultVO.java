package com.linln.admin.device.resultVO;

import lombok.Data;

import java.util.List;

@Data
public class DeviceRegularSafeListResultVO {
    private Long total;
    List<DeviceRegularSafeResultVO> deviceRegularSafeResultVOS;
}
