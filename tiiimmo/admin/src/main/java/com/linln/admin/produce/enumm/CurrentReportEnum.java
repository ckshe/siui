package com.linln.admin.produce.enumm;

import com.linln.common.constant.StatusConst;

public enum CurrentReportEnum {
    /**
     * 正常的数据
     */
    SCLTB("1", "生产流通表"),
    HHLTB("2", "后焊流通表"),
    TPLTB("3", "贴片流通表");



    private String name;

    private String message;

    CurrentReportEnum(String  name, String message) {
        this.name= name;
        this.message = message;
    }
}
