package com.linln.admin.device.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Data
@Proxy(lazy = false)
@DynamicInsert
@DynamicUpdate
@Table(name = "device_safe")
@org.hibernate.annotations.Table(appliesTo = "device_safe", comment = "设备维护内容表")
public class DeviceSafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //维护设备
    private String safeDeviceCode;
    //维护类型
    private String safeType;
    //维护内容
    private String safeContent;
}
