package com.kervin.multidb;

import com.kervin.enumerate.DataSourceEnum;
import org.apache.commons.lang3.StringUtils;


/**
 * 分库分表篮子号计算工具类
 * 此处做了简化，仅使用卡号的第一位对数据源数目求余，作为最终篮子号
 * @author Kervin
 * @since 2018/7/26 20:41
 */
public class ShardingKeyGetter {

    public static DataSourceEnum determineCurrentLookupKey(String shardingkey, Integer dbSize) {
        if (!StringUtils.isNumeric(shardingkey)) {
            return DataSourceEnum.Default;
        }
        Integer dbNum = Integer.parseInt(shardingkey.substring(0, 1) + 1) % dbSize;
        return DataSourceEnum.getEnumByDbNum(dbNum + 1);
    }
}
