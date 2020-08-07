package com.linln.RespAndReqs;

import lombok.Data;

@Data
public class DeviceProductReplaceElementReq {
    // 规格型号
    private String pcbCode;
    // 备注
    private String remark;
    // 原物料
    private String originalProductName;
    // 原物料编号
    private String originalProductCode;
    // 替代料
    private String replaceProductName;
    // 替代料编号
    private String replaceProductCode;
}
