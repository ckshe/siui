package com.linln.admin.produce.request;

import lombok.Data;

@Data
public class CirclateReq {
    private String pcbTaskCode;

    //类型：1贴片2...
    private String type;
}
