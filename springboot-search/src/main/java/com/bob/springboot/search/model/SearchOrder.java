package com.bob.springboot.search.model;

import org.elasticsearch.search.sort.SortOrder;

import java.io.Serializable;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public class SearchOrder implements Serializable {

    private static final long serialVersionUID = 7074401089729844225L;

    //排序字段名称
    private String name;
    //顺序
    private SortOrder sort = SortOrder.ASC;

    public SearchOrder() {}

    public SearchOrder(String name) {
        this.name = name;
    }

    public SearchOrder(String name, SortOrder sort) {
        this.name = name;
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortOrder getSort() {
        return sort;
    }

    public void setSort(SortOrder sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SearchOrder{" +
                "name='" + name + '\'' +
                ", sort=" + sort +
                '}';
    }
}
