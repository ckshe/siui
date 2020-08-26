package com.linln.admin.device.resultVO;

import com.linln.admin.device.VO.DeviceAmbientVO;
import lombok.Data;

import java.util.List;

@Data
public class DeviceAmbientListResultVO {
    private Long total;
    List<DeviceAmbientVO> deviceAmbientVOList;
}
