package com.kervin.multidb;

import com.kervin.exception.CommonRuntimeException;
import com.kervin.enumerate.DataSourceEnum;
import com.kervin.enumerate.SvcRspCdEnum;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 数据源对应SqlSession工厂Builder类
 * @author Kervin
 * @since 2018/7/26 20:41
 */
public final class DataSourceSqlSessionFactoryBuilder {

    private DataSourceSqlSessionFactoryBuilder() {
    }

    // Mybatis配置文件名
    private static final String MYBATIS_CONFIG = "mybatis-config.xml";

    // Mybatis数据源属性配置文件
    private static final String MYBATIS_CONFIG_PROPERTIES = "mybatis-config-datasource.properties";

    // 缓存每个DataSource对应的Mybatis数据源工厂
    private static final Map<DataSourceEnum, SqlSessionFactory> DATASOURCE = new ConcurrentHashMap<>();

    public static SqlSessionFactory buildSqlSessionFactory(DataSourceEnum dataSource) {

        // 返回默认数据源SqlSession工厂
        if (dataSource == null) {
            dataSource = DataSourceEnum.Default;
        }

        // 从缓存中获取请求数据源对应SqlSession工厂
        if (DATASOURCE.get(dataSource) != null) {
            return DATASOURCE.get(dataSource);
        }

        // 根据配置文件创建请求数据源对应SqlSession工厂，并加载到缓存里
        SqlSessionFactory sqlSessionFactory = buildSessionFactoryFromProperties(dataSource);
        DATASOURCE.put(dataSource, sqlSessionFactory);

        return DATASOURCE.get(dataSource);
    }

    /**
     * 根据配置文件创建请求数据源对应SqlSession工厂
     * @param dataSource 请求数据源
     * @return 对应SqlSession工厂
     */
    private static SqlSessionFactory buildSessionFactoryFromProperties(DataSourceEnum dataSource) {

        // 加载MyBatis配置文件
        Properties properties;
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG);
            properties = Resources.getResourceAsProperties(MYBATIS_CONFIG_PROPERTIES);

            // 根据请求数据源替换配置类相应数据库连接实例名
            String url = properties.getProperty("url").toString().replace("%DBNAME%", dataSource.getInstanceName());
            properties.setProperty("url", url);

            // 创建并返回对应SqlSession工厂
            return new SqlSessionFactoryBuilder().build(inputStream, properties);
        } catch (Exception ex) {
            throw new CommonRuntimeException(SvcRspCdEnum.B00000000001, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
