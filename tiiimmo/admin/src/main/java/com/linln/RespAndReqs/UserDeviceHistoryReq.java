package com.linln.RespAndReqs;

import lombok.Data;

import java.util.Date;

@Data
public class UserDeviceHistoryReq {
    private Long id ;
    //机台编号
    private String deviceCode;
    //上机时间
    private Date upTime;

    //下机时间
    private Date downTime;
    //员工姓名
    private String userName;
    //上下机
    private String doType;

    private Byte status;
    //工序计划编号
    private String processTaskCode;

    private Integer page;

    private Integer size;
}
