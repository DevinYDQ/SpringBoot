package com.ydq.config.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDatabaseType(String type) {
        contextHolder.set(type);
    }

    public static String getDatabaseType() {
        return contextHolder.get();
    }

    public static void clearDatabaseType() {
        contextHolder.remove();
    }

    /**
     * 数据源的 key集合，用于切换时判断数据源是否存在
     */
    public static List<Object> dataSourceKeys = new ArrayList<>();

    /**
     * 判断是否包含数据源
     * @param key 数据源key
     * @return
     */
    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }

    /**
     * 添加数据源keys
     * @param keys
     * @return
     */
    public static boolean addDataSourceKeys(Collection<? extends Object> keys) {
        return dataSourceKeys.addAll(keys);
    }
}
