package com.bob.springboot.search.model;

import com.bob.springboot.search.constants.SearchConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public class SearchRequest implements Serializable {

    private static final long serialVersionUID = -1775272965412280972L;

    private List<SearchField> field;
    private List<SearchOrder> order;
    private String indexName;
    private List<String> indexType;
    private Integer pageNo;
    private Integer pageSize;
    private Highlight highlight;

    public class Highlight {

        private boolean highlight = SearchConstants.SEARCH_HIGHLIGHT;

        private String preTag = SearchConstants.HIGHLIGHT_PRE_TAG;

        private String postTag = SearchConstants.HIGHLIGHT_POST_TAG;

        private String highlightFieldName;

        public Highlight() {}

        public Highlight(String highlightFieldName) {
            this.highlightFieldName = highlightFieldName;
            this.highlight = true;
        }

        public Highlight(String highlightFieldName, String preTag, String postTag) {
            this(highlightFieldName);
            this.preTag = preTag;
            this.postTag = postTag;
        }

        public boolean getHighlight() {
            return highlight;
        }

        public void setHighlight(boolean highlight) {
            this.highlight = highlight;
        }

        public String getPreTag() {
            return preTag;
        }

        public void setPreTag(String preTag) {
            this.preTag = preTag;
        }

        public String getPostTag() {
            return postTag;
        }

        public void setPostTag(String postTag) {
            this.postTag = postTag;
        }

        public String getHighlightFieldName() {
            return highlightFieldName;
        }

        public void setHighlightFieldName(String highlightFieldName) {
            this.highlightFieldName = highlightFieldName;
        }
    }

    public Highlight getHighlight() {
        return highlight;
    }

    public void setHighlight(Highlight highlight) {
        this.highlight = highlight;
    }

    public List<SearchField> getField() {
        return field;
    }

    public void setField(List<SearchField> field) {
        this.field = field;
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

    public void setOneIndexType(String indexType) {
        this.indexType = new ArrayList<String>() {{
            add(indexType);
        }};
    }

    public void setOneOrder(SearchOrder order) {
        this.order = new ArrayList<SearchOrder>() {{
            add(order);
        }};
    }

    public void setOneField(SearchField field) {
        this.field = new ArrayList<SearchField>() {{
            add(field);
        }};
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "field=" + field +
                ", order=" + order +
                ", indexName='" + indexName + '\'' +
                ", indexType=" + indexType +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", highlight=" + highlight +
                '}';
    }
}
