package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="produce_pcb_plate_no")
public class PCBPlateNo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    private String pcb_code;

    private String last_plate_no;

    private Integer all_count;

    private Byte status;

    private String prefix;

    private String suffix;


}
