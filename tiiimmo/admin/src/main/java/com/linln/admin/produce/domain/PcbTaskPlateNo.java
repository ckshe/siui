package com.linln.admin.produce.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="produce_pcbtask_plate_no")
public class PcbTaskPlateNo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    private String pcb_code;

    private String pcb_task_code;

    private String plate_no;

    private Byte status;

    //是否已经计数
    private String is_count;

    private Date update_time;
}
