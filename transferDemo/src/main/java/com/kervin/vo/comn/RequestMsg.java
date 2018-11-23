package com.kervin.vo.comn;

/**
 * 请求体
 * @author Kervin
 * @since 2018/7/26 21:47
 */
public class RequestMsg {

    // 公共请求协议头
    private ReqMsgHead msgHead;

    // 交易业务请求参数
    private IBaseInVo inVo;


    public ReqMsgHead getMsgHead() {
        return msgHead;
    }

    public void setMsgHead(ReqMsgHead msgHead) {
        this.msgHead = msgHead;
    }

    public IBaseInVo getInVo() {
        return inVo;
    }

    public void setInVo(IBaseInVo inVo) {
        this.inVo = inVo;
    }
}
