package com.kervin.model;

import com.kervin.entity.AuthEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 授权日志Po
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public class AhnLogPo extends AhnLogPoKey {
    private String txnType;

    private String cardNum;

    private String txnId;

    private String txnDsc;

    private String txnComplStatus;

    private BigDecimal txnAmt;

    private String txnCcy;

    private String txnDt;

    private BigDecimal avlBal;

    private String txnOpntCardNum;

    private Integer vno;

    private Date timeStamps;

    private String svcRspStatus;

    private String svcRspCd;

    private String sysRespDesc;

    public AhnLogPo() {

    }

    public AhnLogPo(AuthEntity entity) {
        this.setSysEvtTraceId(entity.getSysEvtTraceId());
        this.setSysSndSerialNo(entity.getSysSndSerialNo());
        this.txnType = entity.getTxnType();
        this.cardNum = entity.getCardNum();
        this.txnId = entity.getTxnId();
        this.txnDsc = entity.getTxnDsc();
        this.txnComplStatus = entity.getTxnComplStatus();
        this.txnAmt = entity.getTxnAmt();
        this.txnCcy = entity.getTxnCcy();
        this.txnDt = entity.getTxnDt();
        this.avlBal = entity.getAvlBal();
        this.txnOpntCardNum = entity.getTxnOpntCardNum();
        this.svcRspCd = entity.getSvcRspCd();
        this.svcRspStatus = entity.getSvcRspStatus();
        this.sysRespDesc = entity.getSysRespDesc();
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType == null ? null : txnType.trim();
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum == null ? null : cardNum.trim();
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId == null ? null : txnId.trim();
    }

    public String getTxnDsc() {
        return txnDsc;
    }

    public void setTxnDsc(String txnDsc) {
        this.txnDsc = txnDsc == null ? null : txnDsc.trim();
    }

    public String getTxnComplStatus() {
        return txnComplStatus;
    }

    public void setTxnComplStatus(String txnComplStatus) {
        this.txnComplStatus = txnComplStatus == null ? null : txnComplStatus.trim();
    }

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getTxnCcy() {
        return txnCcy;
    }

    public void setTxnCcy(String txnCcy) {
        this.txnCcy = txnCcy == null ? null : txnCcy.trim();
    }

    public String getTxnDt() {
        return txnDt;
    }

    public void setTxnDt(String txnDt) {
        this.txnDt = txnDt == null ? null : txnDt.trim();
    }

    public BigDecimal getAvlBal() {
        return avlBal;
    }

    public void setAvlBal(BigDecimal avlBal) {
        this.avlBal = avlBal;
    }

    public String getTxnOpntCardNum() {
        return txnOpntCardNum;
    }

    public void setTxnOpntCardNum(String txnOpntCardNum) {
        this.txnOpntCardNum = txnOpntCardNum == null ? null : txnOpntCardNum.trim();
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

    public String getSvcRspStatus() {
        return svcRspStatus;
    }

    public void setSvcRspStatus(String svcRspStatus) {
        this.svcRspStatus = svcRspStatus == null ? null : svcRspStatus.trim();
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
}