package com.kervin.enumerate;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源枚举类
 * @author Kervin
 * @since 2018/7/26 22:41
 */
public enum DataSourceEnum {

    Default(0, "database1"),

    DataSource1(1, "database1"),
    
    DataSource2(2, "database2");

    // 代码
    private Integer dbNum;

    // 实例名
    private String instanceName;

    private static final Map<Integer, DataSourceEnum> CODE_TO_ENUM_MAP = new HashMap<Integer, DataSourceEnum>();

    static {
        for (DataSourceEnum e : DataSourceEnum.values()) {
            CODE_TO_ENUM_MAP.put(e.getDbNum(), e);
        }
    }

    public static DataSourceEnum getEnumByDbNum(Integer dbNum) {
        return CODE_TO_ENUM_MAP.get(dbNum);
    }

    DataSourceEnum(Integer dbNum, String instanceName) {
        this.dbNum = dbNum;
        this.instanceName = instanceName;
    }

    public Integer getDbNum() {
        return dbNum;
    }

    public String getInstanceName() {
        return instanceName;
    }
}
