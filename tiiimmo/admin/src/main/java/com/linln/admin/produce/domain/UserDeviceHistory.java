package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_user_device_history")
public class UserDeviceHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    //机台编号
    private String device_code;
    //上下机时间
    private Date do_time;
    //员工id
    private Long user_id;
    //员工姓名
    private String user_name;
    //上下机
    private String do_type;

    private Byte status;
    //工序计划编号
    private String process_task_code;

}
