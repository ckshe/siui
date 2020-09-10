package com.linln.admin.produce.enumm;

public enum CurrentReportEnum {
    /**
     * 正常的数据
     */
    TPLTB("1", "贴片流通表"),
    ZXLTB("2", "整形流通表"),
    HHLTB("3", "后焊流通表"),
    DLBTS("4", "电路板调试"),
    SJJLB("5", "首检记录表"),
    YSJWHJLB("6", "G5全自动视觉印刷机维护记录表"),
    TPJWHJLB("7", "贴片机日常维护记录表"),
    AOIWHJLB("8", "AOI维护记录表"),
    HJJWHJLB("9", "焊接机维护记录表");


    private String name;

    private String message;

    CurrentReportEnum(String name, String message) {
        this.name= name;
        this.message = message;
    }
}
