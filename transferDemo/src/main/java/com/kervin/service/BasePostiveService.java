package com.kervin.service;

import com.kervin.exception.CommonRuntimeException;
import com.kervin.entity.AuthEntity;
import com.kervin.enumerate.ExceptionParams;
import com.kervin.enumerate.SvcRspCdEnum;
import com.kervin.multidb.SessionManager;
import com.kervin.utils.AuthLogOperateUtils;
import com.kervin.utils.validator.ValidatorExecutor;
import com.kervin.vo.comn.IBaseInVo;
import com.kervin.vo.comn.RequestMsg;
import com.kervin.vo.comn.RspMsgHead;

/**
 * 正向服务处理基类
 * @author Kervin
 * @since 2018/7/27 9:29
 */
public abstract class BasePostiveService extends BaseService implements IPostiveService {

    // 服务输入项检查validator链
    protected ValidatorExecutor validator;

    /**
     * 处理正向服务请求
     * @param requestMsg 服务请求体
     * @return 返回Head头
     */
    @Override
    public RspMsgHead doPostiveService(RequestMsg requestMsg) {

        // 将服务请求体转换为授权实体
        AuthEntity entity = buildEntity(requestMsg);

        try {
            // 输入字段校验
            validator.invokeValidators(entity);

            // 插入授权日志
            AuthLogOperateUtils.addAuthLog(entity);

            // 服务业务处理（额度检查，扣减..），日志更新
            authCheck(entity);

        } catch (Exception e) {

            // 异常处理，捕获交易异常，根据异常类型，封装返回Head头，并初始化异步任务
            RspMsgHead rspMsgHead = handleException(entity, e);
            // 更新异常授权日志
            AuthLogOperateUtils.updateAuthLogFail(entity);

            return rspMsgHead;
        } finally {
            // 关闭当前线程所有数据源对应SqlSession
            SessionManager.close();
        }

        System.out.println("卡号: " + entity.getCardNum() + " " + entity.getTxnDsc()
                           + "成功！ 交易金额：" + entity.getTxnAmt() + " 当前余额：" + entity.getAvlBal());

        // 返回服务结果
        return new RspMsgHead();
    }

    /**
     * 将服务请求体转换为授权实体
     * @param requestMsg 服务请求体
     * @return 授权实体
     */
    private AuthEntity buildEntity(RequestMsg requestMsg) {

        if (requestMsg == null || requestMsg.getMsgHead() == null || requestMsg.getInVo() == null) {
            throw new CommonRuntimeException(
                    SvcRspCdEnum.A00000000001, ExceptionParams.RequestMsg.getMessageZH());
        }

        // 填充服务通用授权实体字段
        AuthEntity.Builder builder = new AuthEntity.Builder();
        builder.setSysEvtTraceId(requestMsg.getMsgHead().getSysEvtTraceId())
                .setSysSndSerialNo(requestMsg.getMsgHead().getSysSndSerialNo())
                .setTxnType(service.getTxnType().getCode())
                .setTxnId(service.getCode())
                .setTxnDsc(service.getDesc());

        // 调用子类方法，填充具体服务InVo业务字段到实体中
        return buildEntityFromInvo(builder, requestMsg.getInVo());
    }

    // 服务具体业务处理（额度检查，扣减..）,放到子类实现
    protected abstract void authCheck(AuthEntity entity) throws Exception;

    // 填充具体服务InVo业务字段到实体中
    protected abstract AuthEntity buildEntityFromInvo(AuthEntity.Builder builder, IBaseInVo inVo);

}
