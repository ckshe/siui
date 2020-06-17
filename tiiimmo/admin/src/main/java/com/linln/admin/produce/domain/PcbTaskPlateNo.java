package com.linln.admin.produce.domain;


import lombok.Data;

import javax.persistence.*;

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
}
