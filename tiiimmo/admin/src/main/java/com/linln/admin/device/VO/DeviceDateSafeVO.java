package com.linln.admin.device.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceDateSafeVO {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date safeTime;
    private String safeType;
    private String safeDeviceCode;
    private String safeContent;
    private String safeResult;
    private String safePerson;
}
