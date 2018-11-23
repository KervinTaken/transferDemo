package com.kervin.vo.comn;

/**
 * 请求Head头
 * @author Kervin
 * @since 2018/7/25 22:27
 */
public class ReqMsgHead {

    // 全局事件跟踪号
    private String sysEvtTraceId;

    // 子交易序号
    private String sysSndSerialNo;

    public String getSysEvtTraceId() {
        return sysEvtTraceId;
    }

    public void setSysEvtTraceId(String sysEvtTraceId) {
        this.sysEvtTraceId = sysEvtTraceId;
    }

    public String getSysSndSerialNo() {
        return sysSndSerialNo;
    }

    public void setSysSndSerialNo(String sysSndSerialNo) {
        this.sysSndSerialNo = sysSndSerialNo;
    }
}
