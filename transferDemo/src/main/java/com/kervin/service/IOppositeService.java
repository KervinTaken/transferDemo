package com.kervin.service;

import com.kervin.vo.comn.RspMsgHead;

/**
 * 交易冲正处理接口
 * @author Kervin
 * @since 2018/7/27 9:29
 */
public interface IOppositeService {

    RspMsgHead doOppositeService(String sysEvtTraceId, String sysSndSerialNo, String shardingkey);
}
