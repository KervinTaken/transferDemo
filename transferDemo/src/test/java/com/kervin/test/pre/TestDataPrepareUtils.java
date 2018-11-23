package com.kervin.test.pre;

import com.kervin.dao.CrdBalPoMapper;
import com.kervin.enumerate.DataSourceEnum;
import com.kervin.model.CrdBalPo;
import com.kervin.multidb.SessionManager;
import com.kervin.multidb.ShardingKeyGetter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
 * @author Kervin
 * @since 2018/7/26 20:51
 */
public class TestDataPrepareUtils {

    @Test
    public void testPrepareCrdBal() throws Exception{

        CrdBalPo crdBal = new CrdBalPo();
        crdBal.setCardBal(new BigDecimal(100.00));
        crdBal.setTimeStamps(new Date());
        crdBal.setVno(0);

        for (int i = 0 ; i < 10 ; i ++) {
            for (int j = 0; j < 10; j++) {
                String front = StringUtils.repeat(String.valueOf(i), 8);
                String rear = StringUtils.repeat(String.valueOf(j), 8);
                crdBal.setCardNum(front + rear);
                DataSourceEnum dataSource1 = ShardingKeyGetter.determineCurrentLookupKey(crdBal.getCardNum(), 2);
                SessionManager.get(CrdBalPoMapper.class, dataSource1).insert(crdBal);
            }
        }
    }
}
