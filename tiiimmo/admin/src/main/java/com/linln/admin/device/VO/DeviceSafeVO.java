package com.linln.admin.device.VO;

import lombok.Data;

@Data
public class DeviceSafeVO {
    private Integer id;
    private String safeDeviceCode;
    private String safeType;
    private String safeContent;
}
