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
@Table(name = "device_regular_safe_result")
@org.hibernate.annotations.Table(appliesTo = "device_regular_safe_result", comment = "设备定期维护内容结果")
public class DeviceRegularSafeResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regularSafeId;
    //检测日期
    private Date theSafeTime;
    //设备编号
    private String deviceCode;
    //设备名称
    private String deviceName;
    //检测内容
    private String safeContent;
    //检测值
    private String safeResult;
    //单位
    private String company;
}
