package com.kervin.utils.asyntsk;

import com.kervin.dao.AsynTskPoMapper;
import com.kervin.entity.AuthEntity;
import com.kervin.enumerate.AschnTskStatusEnum;
import com.kervin.enumerate.DataSourceEnum;
import com.kervin.model.AsynTskPo;
import com.kervin.multidb.SessionManager;
import com.kervin.multidb.ShardingKeyGetter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kervin
 * @since 2018/7/28 21:34
 */
public class AsynTskUtils {

    /**
     * 初始化异步冲正服务
     * @param entity 授权实体
     */
    public static void initAsynTsk(AuthEntity entity) {

        AsynTskPo asynTskPo = new AsynTskPo();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time = sdf.format(new Date());
        String rd = RandomStringUtils.randomNumeric(6);
        asynTskPo.setAsynTskNo(time + rd);

        asynTskPo.setMnpltDt(time.substring(0, 8));

        // 设置状态为未执行
        asynTskPo.setAschnTskStatus(AschnTskStatusEnum.NO_EXE.getCode());

        asynTskPo.setCardNum(entity.getTxnOpntCardNum());

        asynTskPo.setExecCnt(0);

        asynTskPo.setMaxExecCnt(6);

        asynTskPo.setTimeStamps(new Date());

        asynTskPo.setVno(0);

        asynTskPo.setSysEvtTraceId(entity.getSysEvtTraceId());

        asynTskPo.setSysSndSerialNo(genNextSysSndSerialNo(entity.getSysSndSerialNo()));

        DataSourceEnum dataSource = ShardingKeyGetter.determineCurrentLookupKey(entity.getCardNum(), 2);
        SessionManager.get(AsynTskPoMapper.class, dataSource).insert(asynTskPo);
    }

    /**
     * 生成异步冲正子交易序号
     * @param sysSndSerialNo 当前子交易序号
     * @return 异步冲正子交易序号
     */
    private static String genNextSysSndSerialNo(String sysSndSerialNo) {
        return StringUtils.leftPad(String.valueOf(Integer.parseInt(sysSndSerialNo) + 1), 4, "0");
    }
}
