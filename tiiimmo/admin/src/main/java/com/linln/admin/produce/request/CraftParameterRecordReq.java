package com.linln.admin.produce.request;

import lombok.Data;

@Data
public class CraftParameterRecordReq {

    //工序任务编号
    private String processTaskCode;

    //id
    private Long id;

    //员工编号
    private String cardSequence;

    private String deviceCode;
}
