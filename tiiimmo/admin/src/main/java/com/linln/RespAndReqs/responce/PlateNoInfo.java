package com.linln.RespAndReqs.responce;

import lombok.Data;

@Data
public class PlateNoInfo {
    private String prefix;

    private String suffix;

    private Integer biginNum;

    private String biginPlateNo;

    private String pcbCode;

    private Long pcbTaskId;

    private Integer year;


}
