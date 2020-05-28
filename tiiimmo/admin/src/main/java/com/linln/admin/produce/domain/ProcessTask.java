package com.linln.admin.produce.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_process_task")
public class ProcessTask {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    // 制造编号
    private String task_sheet_code;
    // 任务号
    private String pcb_task_code;
    // 机型版本
    private String model_ver;
    // PCB名称
    private String pcb_name;
    // PCB数量
    private Integer pcb_quantity;
    // RoHS
    private String is_rohs;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_time;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finish_time;

    //工序名称
    private String process_name;
    //机台名称
    private String device_name;
    //机台编号
    private String device_code;
    //工序单状态
    private String process_task_status;
    //是否冻结
    private Byte status;

    private String process_task_code;

    private Long pcb_task_id;
}

