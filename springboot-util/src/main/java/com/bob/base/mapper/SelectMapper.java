package com.bob.base.mapper;

import com.bob.base.provider.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/12.
 */
public interface SelectMapper<T> {

    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    T getById(Integer id);

    List<T> findAll(T t);

    List<T> findPageList(T t);
}
