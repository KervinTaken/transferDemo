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
import com.kervin.service.IOppositeService;
import com.kervin.vo.comn.RspMsgHead;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 转账冲正服务
 * @author Kervin
 * @since 2018/7/25 22:34
 */
public class TransInOppositeService extends BaseOppositeService {

    // 组合转账转出冲正服务
    private IOppositeService transOutOppService;

    @Override
    protected void authReverse(AuthEntity entity, AhnLogPo origAhnLogPo) throws Exception {

        // 查询卡片余额记录
        DataSourceEnum dataSource = ShardingKeyGetter.determineCurrentLookupKey(origAhnLogPo.getCardNum(), 2);
        CrdBalPo crdBalPo = SessionManager.get(CrdBalPoMapper.class, dataSource).selectByPrimaryKey(origAhnLogPo.getCardNum());

        if (null == crdBalPo) {
            throw new CommonRuntimeException(SvcRspCdEnum.A00000000003, origAhnLogPo.getCardNum());
        } else {
            // 转账转入金额扣减
            crdBalPo.setCardBal(crdBalPo.getCardBal().subtract(origAhnLogPo.getTxnAmt()));
            entity.setAvlBal(crdBalPo.getCardBal());
            crdBalPo.setVno(crdBalPo.getVno() + 1);
            crdBalPo.setTimeStamps(new Date());
        }
        origAhnLogPo.setTxnComplStatus(TxnComplStatusEnum.R.getCode());
        origAhnLogPo.setVno(origAhnLogPo.getVno() + 1);
        origAhnLogPo.setTimeStamps(new Date());

        // 转入方业务规则校验，更新授权日志
        SessionManager.transactional((sqlSession)->{
            int countCrdBal, countAhnLog;
            countCrdBal = sqlSession.getMapper(CrdBalPoMapper.class).updateByPrimaryKey(crdBalPo);
            countAhnLog = sqlSession.getMapper(AhnLogPoMapper.class).updateByPrimaryKey(origAhnLogPo);
            if (countCrdBal != 1 || countAhnLog != 1)
                throw new CommonRuntimeException(SvcRspCdEnum.B00000000004);
            entity.setNeedAysnTsk(true);
        }, dataSource);

        RspMsgHead rspMsgHead;
        try {
            // 组合转账转出冲正服务
            rspMsgHead = transOutOppService.doOppositeService(origAhnLogPo.getSysEvtTraceId(),
                    genNextSysSndSerialNo(entity.getSysSndSerialNo()), origAhnLogPo.getTxnOpntCardNum());
        } catch (Exception e) {
            // 此处不会抛出异常，但在实际分库分表架构中，以微服务组合其他服务时，可能出现超时等其他异常
            throw e;
        }

        // 转账转出冲正服务不成功
        if (!StringUtils.equals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Success.getCode())) {
            throw new CommonRuntimeException(rspMsgHead.getSvcRspCd(), rspMsgHead.getSysRespDesc());
        }
    }


    public TransInOppositeService() {
        service = ServiceEnum.Transfer_In_Opposite;
        transOutOppService = new TransOutOppositeService();
    }
}
