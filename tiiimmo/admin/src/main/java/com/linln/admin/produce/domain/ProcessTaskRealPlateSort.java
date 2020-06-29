package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_process_task_real_platesort")
public class ProcessTaskRealPlateSort {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    private Byte status;

    private String process_task_code;

    private String device_code;

    private Date record_time;

    private String plate_no;
}
