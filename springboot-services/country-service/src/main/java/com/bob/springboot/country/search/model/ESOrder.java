package com.bob.springboot.country.search.model;

import org.elasticsearch.search.sort.SortOrder;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public class ESOrder {

    private String name;
    private SortOrder sort = SortOrder.DESC;

    public ESOrder() {}

    public ESOrder(String name) {
        this.name = name;
    }

    public ESOrder(String name, SortOrder sort) {
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
}
