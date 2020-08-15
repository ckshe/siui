package com.linln.admin.produce.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="produce_craft_parameter_record")
public class CraftParameterRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    //是否冻结
    private Byte status;

    private String device_code;

    private String device_name;

    private String craft_param;

    private String process_task_code;

    private String pcb_task_code;

    private String program_name;

    private String program_status;

    private String class_info;

    private String record_name;

    private String firstInspection_name;

    private Integer plant_count;
}
