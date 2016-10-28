package com.bob.springboot.mapper.mapper;

/**
 * Created by Bob Jiang on 2016/10/12.
 */
public interface DeleteMapper<T> {

    Integer delete(Integer id);

    Integer deleteLogic(Integer id);
}
