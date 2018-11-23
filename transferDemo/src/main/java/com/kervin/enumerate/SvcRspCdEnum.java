package com.kervin.enumerate;

/**
 * 错误码及错误描述枚举
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public enum SvcRspCdEnum {

    SUCCESS("000000000000", "成功"),

    UNKNOWN("999999999999", "未知错误"),

    A00000000001("A00000000001", "@@{0}@@格式不正确"),

    A00000000002("A00000000002", "@@{0}@@不能为空"),

    A00000000003("A00000000003", "卡片@@{0}@@记录不存在"),

    A00000000004("A00000000004", "余额不足"),

    A00000000005("A00000000005", "转账金额不能小于等于0"),

    B00000000001("B00000000001", "参数文件读取异常"),

    B00000000002("B00000000002", "重复交易，拒绝"),

    B00000000003("B00000000003", "数据库操作异常"),

    B00000000004("B00000000004", "数据库更新脏数据异常"),
    ;

    // 错误代码
    private String code;

    // 错误描述
    private String desc;

    SvcRspCdEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
