package com.linln.RespAndReqs;

import lombok.Data;

@Data
public class CardLoginReq {
    private String cardSequence;
    private String deviceCode;
    private String processTaskCode;
}
