package com.kervin.test.utils;

import com.kervin.vo.TransferInVo;
import com.kervin.vo.comn.ReqMsgHead;
import com.kervin.vo.comn.RequestMsg;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 测试请求生成类
 * @author Kervin
 * @since 2018/7/29 23:34
 */
public class RequestMsgGenener {

    public static String genRandomCardNum() {
        Random rd = new Random();
        String front = StringUtils.repeat(String.valueOf(rd.nextInt(10)), 8);
        String rear = StringUtils.repeat(String.valueOf(rd.nextInt(10)), 8);
        return front + rear;
    }

    public static RequestMsg genBaseMsg() {
        RequestMsg requestMsg = new RequestMsg();
        ReqMsgHead head = new ReqMsgHead();
        head.setSysEvtTraceId(RandomStringUtils.randomNumeric(25));
        head.setSysSndSerialNo(RandomStringUtils.randomNumeric(4));
        TransferInVo inVo = new TransferInVo();
        inVo.setCardNum(genRandomCardNum());
        inVo.setTxnAmt(new BigDecimal(2));
        inVo.setTxnCcy("156");
        inVo.setTxnOpntCardNum(genRandomCardNum());
        requestMsg.setInVo(inVo);
        requestMsg.setMsgHead(head);
        return requestMsg;
    }
}
