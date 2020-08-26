package com.linln.admin.device.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class DeviceAmbientForm {
    private Integer ambientId;
    //记录时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
