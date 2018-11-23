package com.kervin.vo;

import com.kervin.vo.comn.IBaseInVo;

import java.math.BigDecimal;

/**
 * 转账服务/转账转出正交易接口
 * @author Kervin
 * @since 2018/7/26 21:34
 */
public class TransferInVo implements IBaseInVo{

    // 卡号
    private String cardNum;

    // 转账金额
    private BigDecimal txnAmt;

    // 转账币种
    private String txnCcy;

    // 对手账号
    private String txnOpntCardNum;

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
}
