package com.kervin.enumerate;

/**
 * 异步任务执行状态枚举
 * @author Kervin
 * @since 2018/7/29 13:51
 */
public enum AschnTskStatusEnum {

    NO_EXE("0", "未执行"),

    EXEING("1", "执行中"),

    SUCCESSED("2", "执行成功"),

    FAILED("3", "执行失败"),
    ;

    AschnTskStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 状态代码
    private String code;

    // 状态描述
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
