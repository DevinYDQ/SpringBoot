package com.ydq.config;

import com.ydq.config.db.DbRoute;
import com.ydq.config.db.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    /**
     * change db
     * @param point
     * @param dbRoute
     */
    @Before("@annotation(dbRoute)")
    //@Before("@annotation("com.ydq.config.db.DbRoute")
    public void switchDataSource(JoinPoint point, DbRoute dbRoute) {
        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) return;

        MethodSignature ms = (MethodSignature) signature;
        Method method = ms.getMethod();
        if (method != null && method.isAnnotationPresent(DbRoute.class)) {
            DbRoute drm = method.getAnnotation(DbRoute.class);
            if (drm != null) {
                DynamicDataSourceContextHolder.setDatabaseType(drm.value().getSimpleName());
            }
        }

//        if (!DynamicDataSourceContextHolder.containDataSourceKey(dbRoute.value().getSimpleName())) {
//            System.out.println("DataSource [{}] doesn't exist, use default DataSource [{}] " + dbRoute.value());
//        } else {
//            // 切换数据源
//            DynamicDataSourceContextHolder.setDatabaseType(dbRoute.value().getSimpleName());
//            System.out.println("Switch DataSource to [" + DynamicDataSourceContextHolder.getDatabaseType()
//                    + "] in Method [" + point.getSignature() + "]");
//        }
    }

    @After("@annotation(dbRoute)")
    public void restoreDataSource(JoinPoint point, DbRoute dbRoute) {
        // 将数据源置为默认数据源
        DynamicDataSourceContextHolder.clearDatabaseType();
        System.out.println("Restore DataSource to [" + DynamicDataSourceContextHolder.getDatabaseType()
                + "] in Method [" + point.getSignature() + "]");
    }

}
