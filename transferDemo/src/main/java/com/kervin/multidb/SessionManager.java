package com.kervin.multidb;

import com.kervin.enumerate.DataSourceEnum;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理线程上下文持有的 SqlSession实例.
 * 此处做了简化，每个请求同一数据源复用同一Session，实际使用可用Spring管理事务，实现分级事务
 * @author Kervin
 * @since 2018/7/26 20:48
 */
public final class SessionManager {

    private SessionManager() {
    }

    //当前线程上下文，持有Mybatis 多数据源对应SqlSession对象.
    private static ThreadLocal<Map<DataSourceEnum, SqlSession>> sqlSessionMapHolder = new ThreadLocal<>();

    /**
     * 获取当前线程持有的请求数据源对应SqlSession对象实例
     * @param dataSource 请求数据源
     * @return 请求数据源对应SqlSession对象实例
     */
    private static SqlSession getOrInitSqlSession(DataSourceEnum dataSource) {
        if (sqlSessionMapHolder.get() != null && sqlSessionMapHolder.get().get(dataSource) != null) {
            return sqlSessionMapHolder.get().get(dataSource);
        } else {
            SqlSessionFactory sqlSessionFactory = DataSourceSqlSessionFactoryBuilder.buildSqlSessionFactory(dataSource);
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            setSqlSession(dataSource, sqlSession);
            return sqlSession;
        }
    }

    /**
     * 设置当前线程持有的SqlSession实例
     * @param dataSource 数据源
     * @param sqlSession 数据源对应SqlSession对象实例
     */
    private static void setSqlSession(DataSourceEnum dataSource, SqlSession sqlSession) {
        Map<DataSourceEnum, SqlSession> sqlSessionMap = sqlSessionMapHolder.get() == null ? new HashMap<>() : sqlSessionMapHolder.get();
        sqlSessionMap.put(dataSource, sqlSession);
        sqlSessionMapHolder.set(sqlSessionMap);
    }

    /**
     * 使用当前线程的SqlSession实例构造指定Mapper实例的代理对象.
     * @param clazz Mapper接口类型.
     * @return Mapper实例的代理对象
     */
    public static <T> T get(Class<T> clazz, DataSourceEnum dataSource) {
        SqlSession session = getOrInitSqlSession(dataSource);
        return session.getMapper(clazz);
    }

    /**
     * 关闭当前线程持有的SqlSession实例并从其ThreadLocal上下文实例中移除
     */
    public static void close() {
        if (sqlSessionMapHolder.get() != null) {
            for (SqlSession sqlSession : sqlSessionMapHolder.get().values())
                sqlSession.close();
        }
        sqlSessionMapHolder.remove();
    }

    /**
     * 数据库事务提交方法
     * @param deltegate 回调接口.
     * @throws Exception
     */
    public static void transactional(ITransactionDelegate deltegate, DataSourceEnum dataSource) throws Exception {
        SqlSession session = getOrInitSqlSession(dataSource);
        try {
            session.commit(false);
            deltegate.execute(session);
            session.commit();
        } catch (Exception ex) {
            session.rollback();
            throw ex;
        } finally {
            session.commit(true);
        }
    }

    //事务代理接口
    public interface ITransactionDelegate {
        public void execute(SqlSession session) throws Exception;
    }
}