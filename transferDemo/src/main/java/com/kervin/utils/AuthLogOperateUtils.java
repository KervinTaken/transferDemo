package com.kervin.utils;

import com.kervin.exception.CommonRuntimeException;
import com.kervin.dao.AhnLogPoMapper;
import com.kervin.entity.AuthEntity;
import com.kervin.enumerate.DataSourceEnum;
import com.kervin.enumerate.SvcRspCdEnum;
import com.kervin.enumerate.SysTxStatusEnum;
import com.kervin.enumerate.TxnComplStatusEnum;
import com.kervin.model.AhnLogPo;
import com.kervin.multidb.SessionManager;
import com.kervin.multidb.ShardingKeyGetter;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.Date;

/**
 * 授权日志操作元件
 * @author Kervin
 * @since 2018/7/26 23:12
 */
public class AuthLogOperateUtils {

    /**
     * 插入授权流水
     * @param entity 授权实体
     */
    public static void addAuthLog(AuthEntity entity) {

        AhnLogPo ahnLogPo = new AhnLogPo(entity);

        ahnLogPo.setTimeStamps(new Date());
        ahnLogPo.setVno(1);

        DataSourceEnum dataSource = ShardingKeyGetter.determineCurrentLookupKey(entity.getCardNum(), 2);
        try {
            SessionManager.get(AhnLogPoMapper.class, dataSource).insert(ahnLogPo);
        } catch (PersistenceException e) {
            Throwable violateException = e.getCause();
            if (violateException instanceof MySQLIntegrityConstraintViolationException) {
                // 拒绝重复交易
                throw new CommonRuntimeException(SvcRspCdEnum.B00000000002, e);
            } else {
                throw new CommonRuntimeException(SvcRspCdEnum.B00000000003, e);
            }
        }

        // 设置失败后需更新授权流水标志
        entity.setNeedUpdateAuthLog(true);
    }

    /**
     * 交易成功更新授权流水
     * @param entity 授权实体
     */
    public static void updateAuthLogSuc(AuthEntity entity) {

        entity.setSvcRspStatus(SysTxStatusEnum.Success.getCode());
        entity.setSvcRspCd(SvcRspCdEnum.SUCCESS.getCode());
        entity.setSysRespDesc(SvcRspCdEnum.SUCCESS.getDesc());
        entity.setTxnComplStatus(TxnComplStatusEnum.S.getCode());

        AhnLogPo ahnLogPo = new AhnLogPo(entity);

        ahnLogPo.setTimeStamps(new Date());
        ahnLogPo.setVno(2);

        DataSourceEnum dataSource = ShardingKeyGetter.determineCurrentLookupKey(entity.getCardNum(), 2);
        int count = SessionManager.get(AhnLogPoMapper.class, dataSource).updateByPrimaryKey(ahnLogPo);
        if (count != 1)
            throw new CommonRuntimeException(SvcRspCdEnum.B00000000004);
    }

    /**
     * 交易失败更新授权流水
     * @param entity 授权实体
     */
    public static void updateAuthLogFail(AuthEntity entity) {

        if (!entity.getNeedUpdateAuthLog())
            return;

        entity.setTxnComplStatus(TxnComplStatusEnum.E.getCode());

        AhnLogPo ahnLogPo = new AhnLogPo(entity);

        ahnLogPo.setTimeStamps(new Date());
        ahnLogPo.setVno(2);

        DataSourceEnum dataSource = ShardingKeyGetter.determineCurrentLookupKey(entity.getCardNum(), 2);
        int count = SessionManager.get(AhnLogPoMapper.class, dataSource).updateByPrimaryKey(ahnLogPo);
        if (count != 1)
            throw new CommonRuntimeException(SvcRspCdEnum.B00000000004);
    }
}
