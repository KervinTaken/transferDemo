package com.kervin.service.impl;

import com.kervin.exception.CommonRuntimeException;
import com.kervin.dao.AhnLogPoMapper;
import com.kervin.dao.CrdBalPoMapper;
import com.kervin.entity.AuthEntity;
import com.kervin.enumerate.*;
import com.kervin.model.AhnLogPo;
import com.kervin.model.CrdBalPo;
import com.kervin.multidb.SessionManager;
import com.kervin.multidb.ShardingKeyGetter;
import com.kervin.service.BaseOppositeService;

import java.util.Date;

/**
 * 转账转出冲正服务
 * @author Kervin
 * @since 2018/7/25 22:34
 */
public class TransOutOppositeService extends BaseOppositeService {

    public TransOutOppositeService() {
        service = ServiceEnum.Transfer_Out_Opposite;
    }

    /**
     * 转账转出冲正服务具体业务处理，额度扣减
     * @param entity 授权实体
     * @param origAhnLogPo 原正交易流水
     */
    @Override
    protected void authReverse(AuthEntity entity, AhnLogPo origAhnLogPo) throws Exception {

        DataSourceEnum dataSource = ShardingKeyGetter.determineCurrentLookupKey(origAhnLogPo.getCardNum(), 2);
        CrdBalPo crdBalPo = SessionManager.get(CrdBalPoMapper.class, dataSource).selectByPrimaryKey(origAhnLogPo.getCardNum());

        if (null == crdBalPo) {
            throw new CommonRuntimeException(SvcRspCdEnum.A00000000003, origAhnLogPo.getCardNum());
        } else {
            crdBalPo.setCardBal(crdBalPo.getCardBal().add(origAhnLogPo.getTxnAmt()));
            entity.setAvlBal(crdBalPo.getCardBal());
            crdBalPo.setTimeStamps(new Date());
            crdBalPo.setVno(crdBalPo.getVno() + 1);
        }
        origAhnLogPo.setTxnComplStatus(TxnComplStatusEnum.R.getCode());
        origAhnLogPo.setVno(origAhnLogPo.getVno() + 1);


        // 转入方业务规则校验，更新授权日志
        SessionManager.transactional((sqlSession)->{
            int countCrdBal, countAhnLog;
            countCrdBal = sqlSession.getMapper(CrdBalPoMapper.class).updateByPrimaryKey(crdBalPo);
            countAhnLog = sqlSession.getMapper(AhnLogPoMapper.class).updateByPrimaryKey(origAhnLogPo);
            if (countCrdBal != 1 || countAhnLog != 1)
                throw new CommonRuntimeException(SvcRspCdEnum.B00000000004);
        }, dataSource);
    }
}
