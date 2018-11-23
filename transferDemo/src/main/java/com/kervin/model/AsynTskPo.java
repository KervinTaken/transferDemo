package com.kervin.model;

import java.util.Date;

/**
 * 异步任务表Po
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public class AsynTskPo {
    private String asynTskNo;

    private String cardNum;

    private String mnpltDt;

    private String aschnTskStatus;

    private Integer maxExecCnt;

    private Integer execCnt;

    private String sysEvtTraceId;

    private String sysSndSerialNo;

    private Date tskStartTm;

    private Date tskEndTm;

    private String svcRspCd;

    private String sysRespDesc;

    private Integer vno;

    private Date timeStamps;

    public String getAsynTskNo() {
        return asynTskNo;
    }

    public void setAsynTskNo(String asynTskNo) {
        this.asynTskNo = asynTskNo == null ? null : asynTskNo.trim();
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum == null ? null : cardNum.trim();
    }

    public String getMnpltDt() {
        return mnpltDt;
    }

    public void setMnpltDt(String mnpltDt) {
        this.mnpltDt = mnpltDt == null ? null : mnpltDt.trim();
    }

    public String getAschnTskStatus() {
        return aschnTskStatus;
    }

    public void setAschnTskStatus(String aschnTskStatus) {
        this.aschnTskStatus = aschnTskStatus == null ? null : aschnTskStatus.trim();
    }

    public Integer getMaxExecCnt() {
        return maxExecCnt;
    }

    public void setMaxExecCnt(Integer maxExecCnt) {
        this.maxExecCnt = maxExecCnt;
    }

    public Integer getExecCnt() {
        return execCnt;
    }

    public void setExecCnt(Integer execCnt) {
        this.execCnt = execCnt;
    }

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

    public Date getTskStartTm() {
        return tskStartTm;
    }

    public void setTskStartTm(Date tskStartTm) {
        this.tskStartTm = tskStartTm;
    }

    public Date getTskEndTm() {
        return tskEndTm;
    }

    public void setTskEndTm(Date tskEndTm) {
        this.tskEndTm = tskEndTm;
    }

    public String getSvcRspCd() {
        return svcRspCd;
    }

    public void setSvcRspCd(String svcRspCd) {
        this.svcRspCd = svcRspCd == null ? null : svcRspCd.trim();
    }

    public String getSysRespDesc() {
        return sysRespDesc;
    }

    public void setSysRespDesc(String sysRespDesc) {
        this.sysRespDesc = sysRespDesc == null ? null : sysRespDesc.trim();
    }

    public Integer getVno() {
        return vno;
    }

    public void setVno(Integer vno) {
        this.vno = vno;
    }

    public Date getTimeStamps() {
        return timeStamps;
    }

    public void setTimeStamps(Date timeStamps) {
        this.timeStamps = timeStamps;
    }
}