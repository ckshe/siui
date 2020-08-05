package com.linln.admin.device.resultVO;

import com.linln.admin.device.VO.DeviceDateSafeVO;
import lombok.Data;

import java.util.List;

@Data
public class DeviceDateSafeResultVO {
    List<String> deviceSafeTypeS;
    List<String> deviceSafeResultS;
    List<DeviceDateSafeVO> deviceDateSafeVOS;
}
