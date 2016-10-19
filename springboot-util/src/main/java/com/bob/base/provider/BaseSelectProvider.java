package com.bob.base.provider;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * Created by Bob Jiang on 2016/10/19.
 */
public class BaseSelectProvider {

    public String getById(MappedStatement ms) {
        StringBuffer sql = new StringBuffer();
        sql.append("select");
        System.out.println(ms.getId());
        return null;
    }
}
