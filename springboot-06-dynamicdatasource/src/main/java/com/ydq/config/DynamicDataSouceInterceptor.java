package com.ydq.config;

import com.ydq.config.db.AllDB;
import com.ydq.config.db.DynamicDataSourceContextHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSouceInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSouceInterceptor.class);
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";
    private static final Map<String, String> cacheMap = new ConcurrentHashMap<>();


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        Object[] objects = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) objects[0];
        String lookupKey = DynamicDataSourceContextHolder.getDatabaseType();
        if (!actualTransactionActive) {
            if (lookupKey == null) lookupKey = cacheMap.get(mappedStatement.getId());
            if (lookupKey == null) {
                if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                    if (mappedStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                        lookupKey = AllDB.Master.class.getSimpleName();
                    } else {
                        BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(objects[1]);
                        String sqlStr = boundSql.getSql();
                        String sql = sqlStr.toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                        if (sql.matches(REGEX)) {
                            lookupKey = AllDB.Master.class.getSimpleName();
                        } else {
                            lookupKey = AllDB.Slave.class.getSimpleName();
                        }
                    }
                }
            }
        }

        if (lookupKey == null) lookupKey = AllDB.Master.class.getSimpleName();
        cacheMap.put(mappedStatement.getId(), lookupKey);
        logger.debug("设置方法[{}] use [{}] Strategy, SqlCommandType [{}]..", mappedStatement.getId(), lookupKey, mappedStatement.getSqlCommandType().name());
        DynamicDataSourceContextHolder.setDatabaseType(lookupKey);
        return invocation.proceed();
    }

    /**
     * 决定返回本体还是编织好的代理类,
     * 代理类会去调用intercept方法决定是使用主库还是从库
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {//Executor是用来支持一系列增删改查操作的，因此需要对该对象进行拦截，其他的放过
            return Plugin.wrap(target, this);
        } else {//返回本体，不做拦截
            return target;
        }
    }

    /**
     * 做一些相关的参数设置，不是关键
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
