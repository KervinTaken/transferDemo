package com.kervin.model;

/**
 * 授权日志主键Po
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public class AhnLogPoKey {
    private String sysEvtTraceId;

    private String sysSndSerialNo;

    public String getSysEvtTraceId() {
        return sysEvtTraceId;
    }

    public void setSysEvtTraceId(String sysEvtTraceId) {
        this.sysEvtTraceId = sysEvtTraceId == null ? null : sysEvtTraceId.trim();
    }

    public String getSysSndSerialNo() {
        return sysSndSerialNo;
    }

    public void setSysSndSerialNo(String sysSndSerialNo) {
        this.sysSndSerialNo = sysSndSerialNo == null ? null : sysSndSerialNo.trim();
    }
}