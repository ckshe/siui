package com.linln.RespAndReqs;

import com.linln.common.vo.ResultVo;
import lombok.Data;

@Data
public class DeviceProductElementReq {
    private Integer page;
    private Integer size;
    // 图样名
    private String sampleName;
    // 机台编号
    private String deviceCode;
    // 物料编号
    private String productCode;
    // 规格型号
    private String pcbCode;
    //AB面
    private String aORb;
}
