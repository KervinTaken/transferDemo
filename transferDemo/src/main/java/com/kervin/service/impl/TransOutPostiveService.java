package com.kervin.service.impl;

import com.kervin.exception.CommonRuntimeException;
import com.kervin.dao.CrdBalPoMapper;
import com.kervin.entity.AuthEntity;
import com.kervin.enumerate.DataSourceEnum;
import com.kervin.enumerate.ExceptionParams;
import com.kervin.enumerate.ServiceEnum;
import com.kervin.enumerate.SvcRspCdEnum;
import com.kervin.model.CrdBalPo;
import com.kervin.multidb.SessionManager;
import com.kervin.multidb.ShardingKeyGetter;
import com.kervin.service.BasePostiveService;
import com.kervin.utils.AuthLogOperateUtils;
import com.kervin.utils.validator.impl.TransferValidatorExecutor;
import com.kervin.vo.TransferInVo;
import com.kervin.vo.comn.IBaseInVo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 转账转出正向服务
 * @author Kervin
 * @since 2018/7/25 22:34
 */
public class TransOutPostiveService extends BasePostiveService {

    // 构造函数，初始化服务类型，输入检查规则链
    public TransOutPostiveService() {
        service = ServiceEnum.Transfer_Out_Positive;
        validator = new TransferValidatorExecutor();
    }

    /**
     * 转账转出业务实现
     * @param entity 授权实体
     */
    @Override
    protected void authCheck(AuthEntity entity) throws Exception {

        // 额度检查及扣减
        DataSourceEnum dataSource = ShardingKeyGetter.determineCurrentLookupKey(entity.getCardNum(), 2);
        CrdBalPo crdBalPo = porcCrdBal(entity, dataSource);

        // // 统一事务更新，授权日志&卡片余额
        SessionManager.transactional((sqlSession)->{
            int count = sqlSession.getMapper(CrdBalPoMapper.class).updateByPrimaryKey(crdBalPo);
            if (count != 1)
                throw new CommonRuntimeException(SvcRspCdEnum.B00000000004);
            AuthLogOperateUtils.updateAuthLogSuc(entity);
        }, dataSource);
    }

    /**
     * 额度检查及扣减
     * @param entity 授权实体
     * @param dataSource 数据源
     * @return 卡片余额Po
     */
    private CrdBalPo porcCrdBal(AuthEntity entity, DataSourceEnum dataSource) {
        // 查询卡片余额记录
        CrdBalPo crdBalPo = SessionManager.get(CrdBalPoMapper.class, dataSource).selectByPrimaryKey(entity.getCardNum());

        // 未找到记录，卡片不存在
        if (null == crdBalPo) {
            throw new CommonRuntimeException(SvcRspCdEnum.A00000000003, entity.getCardNum());
        }

        BigDecimal cardBal = crdBalPo.getCardBal();
        if (cardBal.compareTo(entity.getTxnAmt()) == -1) {
            // 余额不足
            throw new CommonRuntimeException(SvcRspCdEnum.A00000000004);
        } else {
            // 转账转出金额扣减
            crdBalPo.setCardBal(cardBal.subtract(entity.getTxnAmt()));
            entity.setAvlBal(crdBalPo.getCardBal());
            crdBalPo.setVno(crdBalPo.getVno() + 1);
            crdBalPo.setTimeStamps(new Date());
        }

        return crdBalPo;
    }

    /**
     * 填充具体服务InVo业务字段到实体中
     * @param builder 授权实体Builder
     * @param inVo 转账转入InVo
     * @return 授权实体
     */
    @Override
    protected AuthEntity buildEntityFromInvo(AuthEntity.Builder builder, IBaseInVo inVo) {
        if (!(inVo instanceof TransferInVo)) {
            throw new CommonRuntimeException(
                    SvcRspCdEnum.A00000000001, ExceptionParams.InVo.getMessageZH());
        }
        TransferInVo transferInVo = (TransferInVo)inVo;
        return builder.setCardNum(transferInVo.getCardNum())
                .setTxnAmt(transferInVo.getTxnAmt())
                .setTxnCcy(transferInVo.getTxnCcy())
                .setTxnOpntCardNum(transferInVo.getTxnOpntCardNum()).build();
    }
}
