package com.kervin.test;

import com.kervin.enumerate.SvcRspCdEnum;
import com.kervin.enumerate.SysTxStatusEnum;
import com.kervin.service.impl.TransInOppositeService;
import com.kervin.service.impl.TransInPostiveService;
import com.kervin.test.utils.RequestMsgGenener;
import com.kervin.vo.TransferInVo;
import com.kervin.vo.comn.RequestMsg;
import com.kervin.vo.comn.RspMsgHead;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


/**
 * 测试案例集
 * @author Kervin
 * @since 2018/7/27 11:04
 */
public class TestTransService {

    /**
     * 正案例-转账成功
     */
    @Test
    public void testTransServiceSuc() {

        RequestMsg requestMsg = RequestMsgGenener.genBaseMsg();

        TransInPostiveService service = new TransInPostiveService();
        RspMsgHead rspMsgHead = service.doPostiveService(requestMsg);

        Assert.assertEquals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Success.getCode());
        Assert.assertEquals(rspMsgHead.getSvcRspCd(), SvcRspCdEnum.SUCCESS.getCode());
        Assert.assertEquals(rspMsgHead.getSysRespDesc(), SvcRspCdEnum.SUCCESS.getDesc());

        System.out.println("执行成功-正案例-转账成功");
    }

    /**
     * 正案例-转账成功-调用方发起冲正成功
     */
    @Test
    public void testTransferReverse_0() {

        RequestMsg requestMsg = RequestMsgGenener.genBaseMsg();

        TransInPostiveService service = new TransInPostiveService();
        RspMsgHead rspMsgHead = service.doPostiveService(requestMsg);

        Assert.assertEquals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Success.getCode());
        Assert.assertEquals(rspMsgHead.getSvcRspCd(), SvcRspCdEnum.SUCCESS.getCode());
        Assert.assertEquals(rspMsgHead.getSysRespDesc(), SvcRspCdEnum.SUCCESS.getDesc());

        TransInOppositeService oppositeService = new TransInOppositeService();
        rspMsgHead = oppositeService.doOppositeService(requestMsg.getMsgHead().getSysEvtTraceId(),
                requestMsg.getMsgHead().getSysSndSerialNo(),
                ((TransferInVo) requestMsg.getInVo()).getCardNum());

        Assert.assertEquals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Success.getCode());
        Assert.assertEquals(rspMsgHead.getSvcRspCd(), SvcRspCdEnum.SUCCESS.getCode());
        Assert.assertEquals(rspMsgHead.getSysRespDesc(), SvcRspCdEnum.SUCCESS.getDesc());

        System.out.println("执行成功-正案例-转账成功-调用方发起冲正成功");
    }

    /**
     * 正案例-转账失败-调用方发起冲正成功
     */
    @Test
    public void testTransferReverse_1() {

        RequestMsg requestMsg = RequestMsgGenener.genBaseMsg();

        ((TransferInVo)requestMsg.getInVo()).setTxnOpntCardNum("1234567890123456");

        TransInPostiveService service = new TransInPostiveService();
        RspMsgHead rspMsgHead = service.doPostiveService(requestMsg);

        Assert.assertEquals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Fail.getCode());
        Assert.assertEquals(rspMsgHead.getSvcRspCd(), SvcRspCdEnum.A00000000003.getCode());

        TransInOppositeService oppositeService = new TransInOppositeService();
        rspMsgHead = oppositeService.doOppositeService(requestMsg.getMsgHead().getSysEvtTraceId(),
                requestMsg.getMsgHead().getSysSndSerialNo(),
                ((TransferInVo) requestMsg.getInVo()).getCardNum());

        Assert.assertEquals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Success.getCode());
        Assert.assertEquals(rspMsgHead.getSvcRspCd(), SvcRspCdEnum.SUCCESS.getCode());
        Assert.assertEquals(rspMsgHead.getSysRespDesc(), SvcRspCdEnum.SUCCESS.getDesc());

        System.out.println("执行成功-正案例-转账失败-前端发起冲正成功");
    }

    /**
     * 正案例-转账正交易未收到-调用方发起冲正成功
     */
    @Test
    public void testTransferReverse_2() {

        RequestMsg requestMsg = RequestMsgGenener.genBaseMsg();

        TransInOppositeService oppositeService = new TransInOppositeService();
        RspMsgHead rspMsgHead = oppositeService.doOppositeService(requestMsg.getMsgHead().getSysEvtTraceId(),
                requestMsg.getMsgHead().getSysSndSerialNo(),
                ((TransferInVo) requestMsg.getInVo()).getCardNum());

        Assert.assertEquals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Success.getCode());
        Assert.assertEquals(rspMsgHead.getSvcRspCd(), SvcRspCdEnum.SUCCESS.getCode());
        Assert.assertEquals(rspMsgHead.getSysRespDesc(), SvcRspCdEnum.SUCCESS.getDesc());

        System.out.println("执行成功-正案例-转账正交易未收到-前端发起冲正成功");
    }

    /**
     * 反案例-转账失败-转账金额不能为负
     */
    @Test
    public void testTransServiceFail_0() {

        RequestMsg requestMsg = RequestMsgGenener.genBaseMsg();

        ((TransferInVo)requestMsg.getInVo()).setTxnAmt(new BigDecimal(-2));

        TransInPostiveService service = new TransInPostiveService();
        RspMsgHead rspMsgHead = service.doPostiveService(requestMsg);

        Assert.assertEquals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Fail.getCode());
        Assert.assertEquals(rspMsgHead.getSvcRspCd(), SvcRspCdEnum.A00000000005.getCode());

        System.out.println("执行成功-反案例-转出失败-转账金额不能为负");
    }

    /**
     * 反案例-转账失败-转出卡片不存在
     */
    @Test
    public void testTransServiceFail_1() {

        RequestMsg requestMsg = RequestMsgGenener.genBaseMsg();

        ((TransferInVo)requestMsg.getInVo()).setTxnOpntCardNum("1234567890123456");

        TransInPostiveService service = new TransInPostiveService();
        RspMsgHead rspMsgHead = service.doPostiveService(requestMsg);

        Assert.assertEquals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Fail.getCode());
        Assert.assertEquals(rspMsgHead.getSvcRspCd(), SvcRspCdEnum.A00000000003.getCode());

        System.out.println("执行成功-反案例-转账失败-转出卡片不存在");
    }

    /**
     * 反案例-转账失败-转入卡片不存在
     */
    @Test
    public void testTransServiceFail_2() {

        RequestMsg requestMsg = RequestMsgGenener.genBaseMsg();

        ((TransferInVo)requestMsg.getInVo()).setCardNum("1234567890123456");

        TransInPostiveService service = new TransInPostiveService();
        RspMsgHead rspMsgHead = service.doPostiveService(requestMsg);

        Assert.assertEquals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Fail.getCode());
        Assert.assertEquals(rspMsgHead.getSvcRspCd(), SvcRspCdEnum.A00000000003.getCode());

        System.out.println("执行成功-反案例-转账失败-转入卡片不存在-发起异步冲正，请查看守护自动任务进程Console日志");
    }
}
