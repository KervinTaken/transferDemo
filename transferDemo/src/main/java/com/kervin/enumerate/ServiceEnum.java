package com.kervin.enumerate;

/**
 * 服务码枚举
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public enum ServiceEnum {

    Transfer_In_Positive("0000", "转账转入服务", TxnTypeEnum.Postive),

    Transfer_In_Opposite("0001", "转账转入冲正服务", TxnTypeEnum.Opposite),

    Transfer_Out_Positive("0002", "转账转出服务", TxnTypeEnum.Postive),

    Transfer_Out_Opposite("0003", "转账转出冲正服务", TxnTypeEnum.Opposite),;


    // 服务代码
    private String code;

    // 服务描述
    private String desc;

    // 服务方向
    private TxnTypeEnum txnType;

    ServiceEnum(String code, String desc, TxnTypeEnum txnType) {
        this.code = code;
        this.desc = desc;
        this.txnType = txnType;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public TxnTypeEnum getTxnType() {
        return txnType;
    }
}
