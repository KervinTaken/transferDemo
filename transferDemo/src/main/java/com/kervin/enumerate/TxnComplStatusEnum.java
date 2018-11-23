package com.kervin.enumerate;

/**
 * 交易状态枚举
 * @author Kervin
 * @since 2018/7/26 22:53
 */
public enum TxnComplStatusEnum {

    I("I", "初始状态"),

    S("S", "成功完成状态"),

    R("R", "冲正状态"),

    E("E", "错误状态");

    // 状态编码
    private String code;

    // 状态描述
    private String msg;

    TxnComplStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
