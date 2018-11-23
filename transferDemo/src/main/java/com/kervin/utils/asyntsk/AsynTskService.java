package com.kervin.utils.asyntsk;

import com.kervin.exception.CommonRuntimeException;
import com.kervin.dao.AsynTskPoMapper;
import com.kervin.enumerate.AschnTskStatusEnum;
import com.kervin.enumerate.DataSourceEnum;
import com.kervin.enumerate.SvcRspCdEnum;
import com.kervin.enumerate.SysTxStatusEnum;
import com.kervin.model.AsynTskPo;
import com.kervin.multidb.SessionManager;
import com.kervin.service.IOppositeService;
import com.kervin.service.impl.TransOutOppositeService;
import com.kervin.vo.comn.RspMsgHead;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 异步冲正服务
 * @author Kervin
 * @since 2018/7/29 14:06
 */
public class AsynTskService implements Runnable {

    // 当前异步冲正线程处理数据源
    private DataSourceEnum dataSource;
    // 转账转出冲正服务
    private IOppositeService revereService = new TransOutOppositeService();

    public AsynTskService(DataSourceEnum dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run() {

        // 查询待冲正任务记录（最多一次处理10条）
        List<AsynTskPo> asynTskPoList = SessionManager.get(AsynTskPoMapper.class, dataSource).selectAvailTsk();
        for (AsynTskPo asynTskPo : asynTskPoList) {
            procAsynTsk(asynTskPo);
        }
        SessionManager.close();
    }

    /**
     * 依次完成每个异步冲正任务
     * @param asynTskPo 异步冲正Po
     */
    private void procAsynTsk(AsynTskPo asynTskPo) {

        System.out.println("开始执行异步冲正任务，任务流水号： " + asynTskPo.getSysEvtTraceId());

        // 将当前任务设置为执行中，防止多线程时重复执行
        asynTskPo.setVno(asynTskPo.getVno() + 1);
        asynTskPo.setExecCnt(asynTskPo.getExecCnt() + 1);
        asynTskPo.setTskStartTm(new Date());
        asynTskPo.setAschnTskStatus(AschnTskStatusEnum.EXEING.getCode());
        int count = SessionManager.get(AsynTskPoMapper.class, dataSource).updateByPrimaryKey(asynTskPo);
        if (count != 1)
            throw new CommonRuntimeException(SvcRspCdEnum.B00000000004);

        try {
            // 调用转账转出冲正服务，完成异步冲正
            RspMsgHead rspMsgHead = revereService.doOppositeService(asynTskPo.getSysEvtTraceId(),
                    asynTskPo.getSysSndSerialNo(), asynTskPo.getCardNum());
            if (!StringUtils.equals(rspMsgHead.getSvcRspStatus(),SysTxStatusEnum.Success.getCode())) {
                throw new CommonRuntimeException(rspMsgHead.getSvcRspCd(), rspMsgHead.getSysRespDesc());
            }
            asynTskPo.setVno(asynTskPo.getVno() + 1);
            asynTskPo.setTskEndTm(new Date());
            asynTskPo.setAschnTskStatus(AschnTskStatusEnum.SUCCESSED.getCode());
            SessionManager.get(AsynTskPoMapper.class, dataSource).updateByPrimaryKey(asynTskPo);
        } catch (Exception e) {
            asynTskPo.setVno(asynTskPo.getVno() + 1);
            asynTskPo.setTskEndTm(new Date());
            asynTskPo.setAschnTskStatus(AschnTskStatusEnum.FAILED.getCode());
            if (e instanceof CommonRuntimeException) {
                CommonRuntimeException ce = (CommonRuntimeException)e;
                asynTskPo.setSvcRspCd(ce.getSvcRspCd());
                asynTskPo.setSysRespDesc(ce.getErrMessage());
            } else {
                asynTskPo.setSvcRspCd(SvcRspCdEnum.UNKNOWN.getCode());
                asynTskPo.setSysRespDesc(SvcRspCdEnum.UNKNOWN.getDesc());
            }

            SessionManager.get(AsynTskPoMapper.class, dataSource).updateByPrimaryKey(asynTskPo);
        }
    }
}
