package com.bob.springboot.mapper.mapper;

/**
 * Created by Bob Jiang on 2016/10/12.
 */
public interface UpdateMapper<T> {

    Integer update(T t);
}
