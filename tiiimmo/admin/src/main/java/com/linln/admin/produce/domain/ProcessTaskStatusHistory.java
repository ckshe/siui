package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_process_task_status_history")
public class ProcessTaskStatusHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    //shi否冻结
    private Byte status;
    //状态
    private String process_task_status;
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
    //工序任务号
    private String process_task_code;
    //工序名称
    private String process_name;
}
