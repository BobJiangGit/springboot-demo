package com.bob.springboot.country.search.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public class SearchRequest implements Serializable {

    private static final long serialVersionUID = -1775272965412280972L;

    private List<SearchField> searchField;
    private List<ESOrder> order;
    private String indexName;
    private String indexType;
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    public List<SearchField> getSearchField() {
        return searchField;
    }

    public void setSearchField(List<SearchField> searchField) {
        this.searchField = searchField;
    }

    public List<ESOrder> getOrder() {
        return order;
    }

    public void setOrder(List<ESOrder> order) {
        this.order = order;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }
}
