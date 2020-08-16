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
@Table(name = "device_regular_safe_content")
@org.hibernate.annotations.Table(appliesTo = "device_regular_safe_content", comment = "设备定期维护内容表")
public class DeviceRegularSafeContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regularSafeContentId;
    //设备编号
    private String deviceCode;
    //设备名称
    private String deviceName;
    //检测内容
    private String safeContent;
    //单位
    private String company;
}
