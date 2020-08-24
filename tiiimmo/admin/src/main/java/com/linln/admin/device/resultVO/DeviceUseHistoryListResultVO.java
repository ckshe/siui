package com.linln.admin.device.resultVO;

import com.linln.admin.device.VO.DeviceUseHistoryVO;
import lombok.Data;

import java.util.List;

@Data
public class DeviceUseHistoryListResultVO {
    private Long total;
    List<DeviceUseHistoryVO> deviceUseHistoryVOS;
}
