package com.linln.admin.device.resultVO;

import com.linln.admin.device.VO.DeviceSafeVO;
import lombok.Data;

import java.util.List;
@Data
public class DeviceSafeResultVO {
    private Long total;
    List<DeviceSafeVO> deviceSafeVOS;
}
