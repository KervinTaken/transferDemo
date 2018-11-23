package com.kervin.service.impl;

import com.kervin.exception.CommonRuntimeException;
import com.kervin.dao.CrdBalPoMapper;
import com.kervin.entity.AuthEntity;
import com.kervin.enumerate.*;
import com.kervin.model.CrdBalPo;
import com.kervin.multidb.SessionManager;
import com.kervin.multidb.ShardingKeyGetter;
import com.kervin.service.BasePostiveService;
import com.kervin.service.IPostiveService;
import com.kervin.utils.AuthLogOperateUtils;
import com.kervin.utils.validator.impl.TransferValidatorExecutor;
import com.kervin.vo.TransferInVo;
import com.kervin.vo.comn.IBaseInVo;
import com.kervin.vo.comn.ReqMsgHead;
import com.kervin.vo.comn.RequestMsg;
import com.kervin.vo.comn.RspMsgHead;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 转账转入正向服务
 * 处理转入方，并组合转出方服务
 * @author Kervin
 * @since 2018/7/25 22:34
 */
public class TransInPostiveService extends BasePostiveService {

    // 转账转出正向服务
    private IPostiveService transferOutService;

    // 构造函数，初始化服务类型，输入检查规则链，组合服务
    public TransInPostiveService() {
        service = ServiceEnum.Transfer_In_Positive;
        validator = new TransferValidatorExecutor();
        transferOutService = new TransOutPostiveService();
    }

    /**
     * 转账具体业务实现，组合转账转出服务，并完成转入逻辑
     * @param entity 授权实体
     */
    @Override
    protected void authCheck(AuthEntity entity) throws Exception {

        // 组合转出方
        invokeTransOutService(entity);

        // 额度检查及累加
        DataSourceEnum dataSource = ShardingKeyGetter.determineCurrentLookupKey(entity.getCardNum(), 2);
        CrdBalPo crdBalPo = porcCrdBal(entity, dataSource);

        // 统一事务更新，授权日志&卡片余额
        SessionManager.transactional((sqlSession)->{
            int count = sqlSession.getMapper(CrdBalPoMapper.class).updateByPrimaryKey(crdBalPo);
            if (count != 1)
                throw new CommonRuntimeException(SvcRspCdEnum.B00000000004);
            AuthLogOperateUtils.updateAuthLogSuc(entity);
        }, dataSource);
    }

    /**
     * 额度检查及累加
     * @param entity 授权实体
     * @param dataSource 数据源
     * @return 卡片余额Po
     */
    private CrdBalPo porcCrdBal(AuthEntity entity, DataSourceEnum dataSource) {
        // 查询卡片余额记录
        CrdBalPo crdBalPo = SessionManager.get(CrdBalPoMapper.class, dataSource).selectByPrimaryKey(entity.getCardNum());

        if (null == crdBalPo) {
            // 未找到记录，卡片不存在
            throw new CommonRuntimeException(SvcRspCdEnum.A00000000003, entity.getCardNum());
        } else {
            // 转账转入金额累加
            crdBalPo.setCardBal(crdBalPo.getCardBal().add(entity.getTxnAmt()));
            entity.setAvlBal(crdBalPo.getCardBal());
            crdBalPo.setVno(crdBalPo.getVno() + 1);
            crdBalPo.setTimeStamps(new Date());
        }
        return crdBalPo;
    }

    /**
     * 调用转账转出服务，并根据服务结果，初始化异步任务标志
     * @param entity 授权实体
     */
    private void invokeTransOutService(AuthEntity entity) {

        RspMsgHead rspMsgHead;
        try {
            rspMsgHead = transferOutService.doPostiveService(buildTransferOutMsg(entity));
        } catch (Exception e) {
            // 此处不会抛出异常，但在实际分库分表架构中，以微服务组合其他服务时，可能出现超时等其他异常
            entity.setNeedAysnTsk(true);
            throw e;
        }

        // 判断转账转出服务结果，如果不成功，则抛出对应异常
        if (!StringUtils.equals(rspMsgHead.getSvcRspStatus(), SysTxStatusEnum.Success.getCode())) {
            throw new CommonRuntimeException(rspMsgHead.getSvcRspCd(), rspMsgHead.getSysRespDesc());
        }

        // 初始化异步任务标志
        entity.setNeedAysnTsk(true);
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

    /**
     * 构建调用转账转出服务请求体
     * @param entity 授权实体
     * @return 转账转出服务请求体
     */
    private RequestMsg buildTransferOutMsg(AuthEntity entity) {
        RequestMsg requestMsg = new RequestMsg();
        ReqMsgHead head = new ReqMsgHead();
        head.setSysEvtTraceId(entity.getSysEvtTraceId());
        head.setSysSndSerialNo(genNextSysSndSerialNo(entity.getSysSndSerialNo()));
        TransferInVo inVo = new TransferInVo();
        // 转换卡号及对手卡号
        inVo.setCardNum(entity.getTxnOpntCardNum());
        inVo.setTxnOpntCardNum(entity.getCardNum());
        inVo.setTxnAmt(entity.getTxnAmt());
        inVo.setTxnCcy(entity.getTxnCcy());
        requestMsg.setMsgHead(head);
        requestMsg.setInVo(inVo);
        return requestMsg;
    }
}
