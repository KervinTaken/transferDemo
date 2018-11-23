package com.kervin.enumerate;

/**
 * 服务种类枚举
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public enum TxnTypeEnum {

    Postive("0", "正向服务"),

    Opposite("1", "冲正服务");

    // 类型代码
    private String code;

    // 类型描述
    private String desc;

    TxnTypeEnum(String code, String desc) {
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
