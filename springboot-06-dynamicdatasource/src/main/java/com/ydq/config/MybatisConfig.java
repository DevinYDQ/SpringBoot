package com.ydq.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ydq.config.db.AllDB;
import com.ydq.config.db.DynamicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.ydq.dao")
public class MybatisConfig {
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource getMasterDataSouce() {
        return new DruidDataSource();
    }

    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource getSlaveDataSouce() {
        return new DruidDataSource();
    }

    @Bean
    @Primary
    public DynamicDataSource getDataSource(
            @Qualifier("master") DataSource master,
            @Qualifier("slave") DataSource slave) {

        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(AllDB.Master.class.getSimpleName(), master);
        targetDataSources.put(AllDB.Slave.class.getSimpleName(), slave);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(master);

        return dataSource;
    }

    //注意最后createSqlSessionFactory方法中的这一行代码bean.setVfs(SpringBootVFS.class)，对于springboot项目采用java类配置Mybatis的数据源时，mybatis本身的核心库在springboot打包成jar后有个bug，无法完成别名的扫描，在低版本的mybatis-spring-boot-starter中需要自己继承Mybatis核心库中的VFS重写它原有的资源加载方式。在高版本的mybatis-spring-boot-starter已经帮助实现了一个叫SpringBootVFS的类。感兴趣的可以到官方项目了解这个bughttps://github.com/mybatis/spring-boot-starter/issues/177。
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setVfs(SpringBootVFS.class);
        sessionFactoryBean.setDataSource(dynamicDataSource);
//        sessionFactoryBean.setConfigLocation(new DefaultResourceLoader()
//                .getResource("classpath:mybatis.xml"));
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml"));
        sessionFactoryBean.setTypeAliasesPackage("com.ydq.domain");

        Interceptor[] interceptors = new Interceptor[]{
            new DynamicDataSouceInterceptor()
        };
        sessionFactoryBean.setPlugins(interceptors);

        return sessionFactoryBean.getObject();
    }
}
