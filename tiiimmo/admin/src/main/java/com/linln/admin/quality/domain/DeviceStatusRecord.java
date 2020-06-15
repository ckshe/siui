package com.linln.admin.quality.domain;

import com.linln.common.utils.StatusUtil;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="base_device_status_record")

public class DeviceStatusRecord {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    //shi否冻结
    private Byte status;
    //状态
    private String device_status;
    //设备编号
    private String device_code;
    //设备名称
    private String device_name;
    //开始时间
    private Date start_time;
    //结束时间
    private Date end_time;
    //持续时间
    private Integer continue_time;
}
