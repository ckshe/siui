package com.linln.admin.produce.domain;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table(name = "schedule_job")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ScheduleJobApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bean_name")
    private String beanName;

    @Column(name = "params")
    private String params;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "api_status")
    private Integer apiStatus;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "api_condition")
    private String condition;

    @Column(name = "api_key")
    private String key;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "api_url")
    private String apiUrl;

    @Column(name = "select_condition")
    private String selectCondition;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name ="update_number")
    private Integer updateNumber;

    private Byte status;
}
