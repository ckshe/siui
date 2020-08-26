package com.linln.admin.device.VO;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceAmbientVO {
    private Integer ambientId;
    //记录时间
    private Date ambientRecordTime;
    //环境温度
    private String ambientTemperature;
    //环境湿度
    private String ambientHumidity;
    //记录人
    private String ambientRecordPerson;
    //备注
    private String remark;
}
