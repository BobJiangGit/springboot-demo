package com.bob.springboot.search.model;

import com.bob.springboot.search.enums.Clause;
import com.bob.springboot.search.enums.QueryType;

/**
 * 当QueryType为multi_match时，可定义多个fieldName，以逗号分隔，
 * 当QueryType为match_all时，fieldName，value会被忽略，匹配所有。
 *
 * Created by Bob Jiang on 2016/10/27.
 */
public class SearchField {

    //字段名
    private String fieldName;
    //字段值
    private Object value;
    //查询方式
    private QueryType type = QueryType.term;
    //匹配条件
    private Clause clause = Clause.should;


    public SearchField() {}

    public SearchField(String fieldName, Object value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    public SearchField(String fieldName, Object value, QueryType type) {
        this(fieldName, value);
        this.type = type;
    }

    public SearchField(String fieldName, Object value, QueryType type, Clause clause) {
        this(fieldName, value, type);
        this.clause = clause;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public QueryType getType() {
        return type;
    }

    public void setType(QueryType type) {
        this.type = type;
    }

    public Clause getClause() {
        return clause;
    }

    public void setClause(Clause clause) {
        this.clause = clause;
    }

    @Override
    public String toString() {
        return "SearchField{" +
                "fieldName='" + fieldName + '\'' +
                ", value=" + value +
                ", type=" + type +
                ", clause=" + clause +
                '}';
    }
}
