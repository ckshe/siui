package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_process_task_device")
public class ProcessTaskDevice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    //工序计划编号
    private String process_task_code;
    //设备编号
    private String device_code;
    //当前版编号
    private String plant_code;
    //上机用户
    private String user_ids;
    //时间
    private Date time_stamp;
    //计数
    private Integer amount;
    //状态
    private String td_status;

    private Byte status;
}
