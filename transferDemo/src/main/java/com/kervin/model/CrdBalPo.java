package com.kervin.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 卡片余额表Po
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public class CrdBalPo {
    private String cardNum;

    private BigDecimal cardBal;

    private Integer vno;

    private Date timeStamps;

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum == null ? null : cardNum.trim();
    }

    public BigDecimal getCardBal() {
        return cardBal;
    }

    public void setCardBal(BigDecimal cardBal) {
        this.cardBal = cardBal;
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