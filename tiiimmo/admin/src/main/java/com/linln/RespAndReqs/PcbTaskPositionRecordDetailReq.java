package com.linln.RespAndReqs;

import lombok.Data;

@Data
public class PcbTaskPositionRecordDetailReq {
    private Long id ;
    private Byte status;
    // 记录id
    private Long recordId;
    // 设备号
    private String deviceCode;
    // 排产任务号
    private String pcbTaskCode;
    // 工序任务号
    private String processTaskCode;
    // 元件名
    private String elementName;
    // 物料编号
    private String productCode;
    // 安装位置
    private String position;
    // 安装状态 0无1位置确认2已插入
    private String installStatus;
    // 扫描的物料编号
    private String lastProductCode;
    // 扫描的元件名
    private String lastElementName;
    // 页数
    private Integer page;
    // 页面大小
    private Integer size;
}
