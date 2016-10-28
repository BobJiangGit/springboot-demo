package com.bob.springboot.search.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public class SearchRequest implements Serializable {

    private static final long serialVersionUID = -1775272965412280972L;

    private List<SearchField> searchField;
    private List<SearchOrder> order;
    private String indexName;
    private List<String> indexType;
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    public List<SearchField> getSearchField() {
        return searchField;
    }

    public void setSearchField(List<SearchField> searchField) {
        this.searchField = searchField;
    }

    public List<SearchOrder> getOrder() {
        return order;
    }

    public void setOrder(List<SearchOrder> order) {
        this.order = order;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo != null && pageNo.intValue() > 0)
            this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize.intValue() > 0)
            this.pageSize = pageSize;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public List<String> getIndexType() {
        return indexType;
    }

    public void setIndexType(List<String> indexType) {
        this.indexType = indexType;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "searchField=" + searchField +
                ", order=" + order +
                ", indexName='" + indexName + '\'' +
                ", indexType='" + indexType + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
