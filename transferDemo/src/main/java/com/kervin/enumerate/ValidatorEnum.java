package com.kervin.enumerate;

/**
 * 输入项校验类枚举
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public enum ValidatorEnum {

    SysEvtTraceIdValidator("全局流水号校验"),

    SysSndSerialNoValidator("子交易序号校验"),

    CardNumValidator("卡号校验"),

    TxnAmtValidator("交易金额校验"),

    TxnCcyValidator("交易币种校验"),

    OpntCardNumValidator("对手卡号校验"),
    ;

    // 校验描述
    private String desc;

    ValidatorEnum(String desc) {
        this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }
}
