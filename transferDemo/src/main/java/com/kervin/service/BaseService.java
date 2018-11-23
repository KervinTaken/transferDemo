package com.kervin.service;

import com.kervin.exception.CommonRuntimeException;
import com.kervin.entity.AuthEntity;
import com.kervin.enumerate.ServiceEnum;
import com.kervin.enumerate.SvcRspCdEnum;
import com.kervin.enumerate.SysTxStatusEnum;
import com.kervin.utils.asyntsk.AsynTskUtils;
import com.kervin.vo.comn.RspMsgHead;
import org.apache.commons.lang3.StringUtils;

/**
 * 正向/冲正服务公共基类
 * @author Kervin
 * @since 2018/7/27 9:29
 */
public abstract class BaseService {

    // 服务类型属性
    protected ServiceEnum service;

    /**
     * 异常处理方法，捕获交易异常，根据异常类型，封装返回Head头，并初始化异步任务
     * @param entity 授权实体
     * @param e 交易异常
     * @return 返回Head头
     */
    protected RspMsgHead handleException(AuthEntity entity, Exception e) {

        //e.printStackTrace();

        if (e instanceof CommonRuntimeException) {
            CommonRuntimeException ce = (CommonRuntimeException)e;
            entity.setSvcRspCd(ce.getSvcRspCd());
            entity.setSysRespDesc(ce.getErrMessage());
            entity.setSvcRspStatus(SysTxStatusEnum.Fail.getCode());
        } else {
            entity.setSvcRspCd(SvcRspCdEnum.UNKNOWN.getCode());
            entity.setSysRespDesc(SvcRspCdEnum.UNKNOWN.getDesc());
            entity.setSvcRspStatus(SysTxStatusEnum.Fail.getCode());
        }

        RspMsgHead rspMsgHead = buildMsgHead(entity);

        if (entity.getNeedAysnTsk())
            AsynTskUtils.initAsynTsk(entity);

        return rspMsgHead;
    }

    /**
     * 生成组合调用服务时下一子交易序号
     * @param sysSndSerialNo 当前子交易序号
     * @return 调用下次服务子交易序号
     */
    protected String genNextSysSndSerialNo(String sysSndSerialNo) {
        return StringUtils.leftPad(String.valueOf(Integer.parseInt(sysSndSerialNo) + 1), 4, "0");
    }

    /**
     * 根据授权实体，生成返回Head头
     * @param entity 授权实体
     * @return 返回Head头
     */
    private RspMsgHead buildMsgHead(AuthEntity entity) {
        RspMsgHead rspMsgHead = new RspMsgHead();
        rspMsgHead.setSvcRspCd(entity.getSvcRspCd());
        rspMsgHead.setSvcRspStatus(entity.getSvcRspStatus());
        rspMsgHead.setSysRespDesc(entity.getSysRespDesc());
        return rspMsgHead;
    }
}
