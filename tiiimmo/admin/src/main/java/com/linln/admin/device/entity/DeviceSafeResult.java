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
@Table(name = "device_safe_result")
@org.hibernate.annotations.Table(appliesTo = "device_safe_result", comment = "设备维护结果表")
public class DeviceSafeResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //维护结果
    private String safeResult;
    //默认选择
    private Boolean defaultResult;
}
