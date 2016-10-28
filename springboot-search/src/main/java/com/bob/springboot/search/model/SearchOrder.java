package com.bob.springboot.search.model;

import org.elasticsearch.search.sort.SortOrder;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public class SearchOrder {

    private String name;
    private SortOrder sort = SortOrder.DESC;

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
        return "ESOrder{" +
                "name='" + name + '\'' +
                ", sort=" + sort +
                '}';
    }
}
