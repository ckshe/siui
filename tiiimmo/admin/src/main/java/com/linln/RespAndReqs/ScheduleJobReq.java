package com.linln.RespAndReqs;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleJobReq {
    private String key;
    private String action;
    private String where;
    private String desc;
    private List<String> paramList;
    private Integer pageSize;
    private Integer pageIndex;
    private Integer isReRun;
    private String url;
    private String select;
}
