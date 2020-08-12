package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="produce_pcbtask_first_plate_no")
public class PcbTaskFirstPlateNo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    private Byte status;
    private String pcb_code;
    private String prefix;
    private String suffix;
    private Integer first_no;
    private String pcb_task_code;

    private Integer year;

}
