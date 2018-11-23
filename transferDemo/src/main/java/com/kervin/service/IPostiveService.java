package com.kervin.service;

import com.kervin.vo.comn.RequestMsg;
import com.kervin.vo.comn.RspMsgHead;

/**
 * 交易正向处理接口
 * @author Kervin
 * @since 2018/7/27 9:29
 */
public interface IPostiveService {

    RspMsgHead doPostiveService(RequestMsg requestMsg);
}
