package com.kervin.multidb;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * C3P0连接池Factory类
 * @author Kervin
 * @since 2018/7/26 21:01
 */
public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {
    public C3P0DataSourceFactory(){
        this.dataSource=new ComboPooledDataSource();
    }
}