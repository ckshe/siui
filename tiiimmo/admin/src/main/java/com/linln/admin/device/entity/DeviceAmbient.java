package com.linln.admin.device.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Proxy(lazy = false)
@DynamicInsert
@DynamicUpdate
@Table(name = "device_ambient")
@org.hibernate.annotations.Table(appliesTo = "device_ambient", comment = "车间环境记录表")
public class DeviceAmbient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
