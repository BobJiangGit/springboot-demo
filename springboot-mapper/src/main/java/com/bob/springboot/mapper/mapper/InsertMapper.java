package com.bob.springboot.mapper.mapper;


/**
 * Created by Bob Jiang on 2016/10/12.
 */
public interface InsertMapper<T> {

    Integer save(T t);
}
