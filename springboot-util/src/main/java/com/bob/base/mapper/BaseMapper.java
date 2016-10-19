package com.bob.base.mapper;


/**
 * Created by Bob Jiang on 2016/10/12.
 */
public interface BaseMapper<T> extends
        InsertMapper<T>,
        UpdateMapper<T>,
        DeleteMapper<T>,
        SelectMapper<T> {

}
