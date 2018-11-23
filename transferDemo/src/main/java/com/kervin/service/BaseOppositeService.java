package com.kervin.service;

import com.kervin.dao.AhnLogPoMapper;
import com.kervin.entity.AuthEntity;
import com.kervin.enumerate.DataSourceEnum;
import com.kervin.enumerate.TxnComplStatusEnum;
import com.kervin.enumerate.TxnTypeEnum;
import com.kervin.model.AhnLogPo;
import com.kervin.model.AhnLogPoKey;
import com.kervin.multidb.SessionManager;
import com.kervin.multidb.ShardingKeyGetter;
import com.kervin.vo.comn.RspMsgHead;
import org.apache.commons.lang3.StringUtils;

/**
 * 反向冲正交易基类
 * @author Kervin
 * @since 2018/7/27 9:29
 */
public abstract class BaseOppositeService extends BaseService implements IOppositeService {

    /**
     * 处理反向冲正服务请求
     * @param sysEvtTraceId 全局流水号
     * @param sysSndSerialNo 子交易序号
     * @param shardingkey 分区键(卡号)
     * @return 返回Head头
     */
    @Override
    public RspMsgHead doOppositeService(String sysEvtTraceId, String sysSndSerialNo, String shardingkey) {

        // 根据全局流水号，子交易序号查询反向冲正交易对应正交易授权日志
        DataSourceEnum dataSource = ShardingKeyGetter.determineCurrentLookupKey(shardingkey, 2);
        AhnLogPoKey ahnLogPoKey = new AhnLogPoKey();
        ahnLogPoKey.setSysEvtTraceId(sysEvtTraceId);
        ahnLogPoKey.setSysSndSerialNo(sysSndSerialNo);
        AhnLogPo origAhnLogPo = SessionManager.get(AhnLogPoMapper.class, dataSource).selectByPrimaryKey(ahnLogPoKey);

        // 未找到对应正交易授权日志（即未收到正交易），或正交易未成功，返回冲正成功
        if (origAhnLogPo == null || !StringUtils.equalsIgnoreCase(origAhnLogPo.getTxnComplStatus(), TxnComplStatusEnum.S.getCode())) {
            SessionManager.close();
            return new RspMsgHead();
        }

        // 根据反向冲正交易对应正交易授权日志构建冲正授权实体
        AuthEntity revEntity = buildEntity(origAhnLogPo);

        try {
            // 反向冲正服务具体业务处理（额度恢复..），日志更新
            authReverse(revEntity, origAhnLogPo);

        } catch (Exception e) {
            // 异常处理方法，捕获交易异常，根据异常类型，封装返回Head头，并初始化异步任务
            return handleException(revEntity, e);
        } finally {
            // 关闭当前线程所有数据源对应SqlSession
            SessionManager.close();
        }

        System.out.println("卡号: " + revEntity.getCardNum() + " " + revEntity.getTxnDsc()
                + "成功！ 交易金额：" + revEntity.getTxnAmt() + " 当前余额：" + revEntity.getAvlBal());

        return new RspMsgHead();
    }

    /**
     * 根据反向冲正交易对应正交易授权日志构建冲正授权实体
     * @param origAhnLogPo 反向冲正交易对应正交易授权日志
     * @return 冲正授权实体
     */
    private AuthEntity buildEntity(AhnLogPo origAhnLogPo) {

        AuthEntity.Builder builder = AuthEntity.Builder.getInstance();
        return builder.setSysEvtTraceId(origAhnLogPo.getSysEvtTraceId())
                .setSysSndSerialNo(origAhnLogPo.getSysSndSerialNo())
                .setTxnType(TxnTypeEnum.Opposite.getCode())
                .setTxnId(service.getCode())
                .setTxnDsc(service.getDesc())
                .setCardNum(origAhnLogPo.getCardNum())
                .setTxnAmt(origAhnLogPo.getTxnAmt())
                .setTxnCcy(origAhnLogPo.getTxnCcy())
                .setTxnOpntCardNum(origAhnLogPo.getTxnOpntCardNum()).build();
    }

    // 反向冲正服务具体业务处理（额度恢复..）,放到子类实现
    protected abstract void authReverse(AuthEntity entity, AhnLogPo origAhnLogPo) throws Exception;
}
