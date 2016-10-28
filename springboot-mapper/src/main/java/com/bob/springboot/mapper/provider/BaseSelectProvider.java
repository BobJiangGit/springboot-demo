package com.bob.springboot.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * Created by Bob Jiang on 2016/10/19.
 */
public class BaseSelectProvider {

    public String getById(Integer id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select");
        return null;
    }

    public String findAll() {
        StringBuffer sql = new StringBuffer();
        sql.append("select");
//        System.out.println("============================================" + ms.getId());
        return null;
    }

    public String dynamicSQL(Object record) {
        return "dynamicSQL";
    }
}
