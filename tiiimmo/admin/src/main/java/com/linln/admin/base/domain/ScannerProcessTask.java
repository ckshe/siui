package com.linln.admin.base.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="base_scanner_process_task")
public class ScannerProcessTask {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    private Byte status;

    private String processTaskCode;

    private String device_code;

    private Integer device_sort;
}
