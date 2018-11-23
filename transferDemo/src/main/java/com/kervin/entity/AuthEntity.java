package com.kervin.entity;

import com.kervin.enumerate.SvcRspCdEnum;
import com.kervin.enumerate.SysTxStatusEnum;
import com.kervin.enumerate.TxnComplStatusEnum;
import com.kervin.enumerate.TxnTypeEnum;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 授权实体
 * @author Kervin
 * @since 2018/7/26 21:26
 */
public class AuthEntity {


    /*****请求公共实体部分****************************************************************************/

    // 全局事件跟踪号
    private String sysEvtTraceId;
    // 子交易序号
    private String sysSndSerialNo;

    /*****请求业务实体部分****************************************************************************/

    // 卡号
    private String cardNum;
    // 转账金额
    private BigDecimal txnAmt;
    // 转账币种
    private String txnCcy;
    // 对手账号
    private String txnOpntCardNum;

    /*****预处理实体部分****************************************************************************/

    // 服务类型--正/反
    private String txnType;
    // 服务ID
    private String txnId;
    // 服务描述
    private String txnDsc;
    // 交易日期
    private String txnDt;
    // 可用额度
    private BigDecimal avlBal;

    /*****检查结果实体部分****************************************************************************/

    // 交易完成状态
    private String txnComplStatus;

    /*****响应公共实体部分****************************************************************************/

    // 服务响应状态
    private String svcRspStatus;
    // 服务响应码
    private String svcRspCd;
    // 服务响应描述
    private String sysRespDesc;

    /*****中间变量实体部分****************************************************************************/

    // 是否需要更新授权日志
    private Boolean needUpdateAuthLog;
    // 是否需要发起异步冲正
    private Boolean needAysnTsk;


    private AuthEntity(Builder builder) {
        this.sysEvtTraceId = builder.sysEvtTraceId;
        this.sysSndSerialNo = builder.sysSndSerialNo;
        this.txnId = builder.getTxnId();
        this.cardNum = builder.cardNum;
        this.txnAmt = builder.txnAmt;
        this.txnCcy = builder.txnCcy;
        this.txnOpntCardNum = builder.txnOpntCardNum;
        this.txnType = builder.txnType;
        this.txnDsc = builder.txnDsc;
        this.txnDt = builder.txnDt;
        this.avlBal = builder.avlBal;
        this.txnComplStatus = builder.txnComplStatus;
        this.svcRspStatus = builder.svcRspStatus;
        this.svcRspCd = builder.svcRspCd;
        this.sysRespDesc = builder.sysRespDesc;
        this.needUpdateAuthLog = builder.needUpdateAuthLog;
        this.needAysnTsk = builder.needAysnTsk;
    }

    public static class Builder {

        // 全局事件跟踪号
        private String sysEvtTraceId;
        // 子交易序号
        private String sysSndSerialNo;
        // 服务类型--正/反
        private String txnType;
        // 卡号
        private String cardNum;
        // 转账金额
        private BigDecimal txnAmt;
        // 转账币种
        private String txnCcy;
        // 对手账号
        private String txnOpntCardNum;
        // 服务ID
        private String txnId;
        // 服务描述
        private String txnDsc;
        // 交易日期
        private String txnDt;
        // 可用额度
        private BigDecimal avlBal;
        // 交易完成状态
        private String txnComplStatus;
        // 服务响应状态
        private String svcRspStatus;
        // 服务响应码
        private String svcRspCd;
        // 服务响应描述
        private String sysRespDesc;
        // 是否需要更新授权日志
        private Boolean needUpdateAuthLog;
        // 是否需要发起异步冲正
        private Boolean needAysnTsk;

        public Builder() {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateStr = sdf.format(date);
            this.txnDt = new String(dateStr.substring(0, 8));
            this.txnType = TxnTypeEnum.Postive.getCode();
            this.txnComplStatus = TxnComplStatusEnum.I.getCode();
            this.svcRspStatus = SysTxStatusEnum.Fail.getCode();
            this.svcRspCd = SvcRspCdEnum.UNKNOWN.getCode();
            this.sysRespDesc = SvcRspCdEnum.UNKNOWN.getDesc();
            this.needAysnTsk = false;
            this.needUpdateAuthLog = false;
        }

        public static Builder getInstance() {
            return new Builder();
        }

        public AuthEntity build() {
            return new AuthEntity(this);
        }

        public String getSysEvtTraceId() {
            return sysEvtTraceId;
        }

        public Builder setSysEvtTraceId(String sysEvtTraceId) {
            this.sysEvtTraceId = sysEvtTraceId;
            return this;
        }

        public String getSysSndSerialNo() {
            return sysSndSerialNo;
        }

        public Builder setSysSndSerialNo(String sysSndSerialNo) {
            this.sysSndSerialNo = sysSndSerialNo;
            return this;
        }

        public String getCardNum() {
            return cardNum;
        }

        public Builder setCardNum(String cardNum) {
            this.cardNum = cardNum;
            return this;
        }

        public BigDecimal getTxnAmt() {
            return txnAmt;
        }

        public Builder setTxnAmt(BigDecimal txnAmt) {
            this.txnAmt = txnAmt;
            return this;
        }

        public String getTxnCcy() {
            return txnCcy;
        }

        public Builder setTxnCcy(String txnCcy) {
            this.txnCcy = txnCcy;
            return this;
        }

        public String getTxnOpntCardNum() {
            return txnOpntCardNum;
        }

        public Builder setTxnOpntCardNum(String txnOpntCardNum) {
            this.txnOpntCardNum = txnOpntCardNum;
            return this;
        }

        public String getTxnType() {
            return txnType;
        }

        public Builder setTxnType(String txnType) {
            this.txnType = txnType;
            return this;
        }

        public String getTxnDsc() {
            return txnDsc;
        }

        public Builder setTxnDsc(String txnDsc) {
            this.txnDsc = txnDsc;
            return this;
        }

        public String getTxnDt() {
            return txnDt;
        }

        public Builder setTxnDt(String txnDt) {
            this.txnDt = txnDt;
            return this;
        }

        public BigDecimal getAvlBal() {
            return avlBal;
        }

        public Builder setAvlBal(BigDecimal avlBal) {
            this.avlBal = avlBal;
            return this;
        }

        public String getTxnComplStatus() {
            return txnComplStatus;
        }

        public Builder setTxnComplStatus(String txnComplStatus) {
            this.txnComplStatus = txnComplStatus;
            return this;
        }

        public String getSvcRspStatus() {
            return svcRspStatus;
        }

        public Builder setSvcRspStatus(String svcRspStatus) {
            this.svcRspStatus = svcRspStatus;
            return this;
        }

        public String getSvcRspCd() {
            return svcRspCd;
        }

        public Builder setSvcRspCd(String svcRspCd) {
            this.svcRspCd = svcRspCd;
            return this;
        }

        public String getSysRespDesc() {
            return sysRespDesc;
        }

        public Builder setSysRespDesc(String sysRespDesc) {
            this.sysRespDesc = sysRespDesc;
            return this;
        }

        public String getTxnId() {
            return txnId;
        }

        public Builder setTxnId(String txnId) {
            this.txnId = txnId;
            return this;
        }

        public Boolean getNeedUpdateAuthLog() {
            return needUpdateAuthLog;
        }

        public Builder setNeedUpdateAuthLog(Boolean needUpdateAuthLog) {
            this.needUpdateAuthLog = needUpdateAuthLog;
            return this;
        }

        public Boolean getNeedAysnTsk() {
            return needAysnTsk;
        }

        public Builder setNeedAysnTsk(Boolean needAysnTsk) {
            this.needAysnTsk = needAysnTsk;
            return this;
        }
    }

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

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
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
        this.txnCcy = txnCcy;
    }

    public String getTxnOpntCardNum() {
        return txnOpntCardNum;
    }

    public void setTxnOpntCardNum(String txnOpntCardNum) {
        this.txnOpntCardNum = txnOpntCardNum;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnDsc() {
        return txnDsc;
    }

    public void setTxnDsc(String txnDsc) {
        this.txnDsc = txnDsc;
    }

    public String getTxnDt() {
        return txnDt;
    }

    public void setTxnDt(String txnDt) {
        this.txnDt = txnDt;
    }

    public BigDecimal getAvlBal() {
        return avlBal;
    }

    public void setAvlBal(BigDecimal avlBal) {
        this.avlBal = avlBal;
    }

    public String getTxnComplStatus() {
        return txnComplStatus;
    }

    public void setTxnComplStatus(String txnComplStatus) {
        this.txnComplStatus = txnComplStatus;
    }

    public String getSvcRspStatus() {
        return svcRspStatus;
    }

    public void setSvcRspStatus(String svcRspStatus) {
        this.svcRspStatus = svcRspStatus;
    }

    public String getSvcRspCd() {
        return svcRspCd;
    }

    public void setSvcRspCd(String svcRspCd) {
        this.svcRspCd = svcRspCd;
    }

    public String getSysRespDesc() {
        return sysRespDesc;
    }

    public void setSysRespDesc(String sysRespDesc) {
        this.sysRespDesc = sysRespDesc;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public Boolean getNeedUpdateAuthLog() {
        return needUpdateAuthLog;
    }

    public void setNeedUpdateAuthLog(Boolean needUpdateAuthLog) {
        this.needUpdateAuthLog = needUpdateAuthLog;
    }

    public Boolean getNeedAysnTsk() {
        return needAysnTsk;
    }

    public void setNeedAysnTsk(Boolean needAysnTsk) {
        this.needAysnTsk = needAysnTsk;
    }
}
