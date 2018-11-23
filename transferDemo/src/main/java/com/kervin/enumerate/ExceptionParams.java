package com.kervin.enumerate;

/**
 * 错误描述参数枚举
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public enum ExceptionParams {

    RequestMsg("请求体"),

    InVo("业务请求体"),

    SysEvtTraceId("全局流水号"),

    SysSndSerialNo("子交易序号"),


    CardNum("卡号"),

    TxnAmt("交易金额"),

    TxnCcy("交易币种"),

    OpntCardNum("对手卡号"),

    ;

    // 错误中文描述
    String messageZH;

    ExceptionParams(String messageZH) {
        this.messageZH = messageZH;
    }

    public String getMessageZH() {
        return messageZH;
    }

}
