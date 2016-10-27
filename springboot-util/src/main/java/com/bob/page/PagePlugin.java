package com.bob.page;

import com.bob.enums.Dialect;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

/**
 * Created by Bob Jiang on 2016/10/26.
 */
public class PagePlugin implements Interceptor {

    private int startRow;
    private int endRow;
    private Dialect dialect;
    private static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal<Page>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object o) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
