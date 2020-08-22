package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_process_task_handover")
//承接人交接人记录
public class ProcessTaskHandover {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    private Byte status;

    private String device_code;

    //1交接人2承接人
    private String type;

    private String process_task_code;

    private Integer count;

    private String pcb_task_code;

    private Date create_time;

    private String user_name;

    @Transient
    private String cardSequence;
}
