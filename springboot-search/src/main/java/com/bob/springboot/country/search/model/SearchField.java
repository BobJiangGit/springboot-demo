package com.bob.springboot.country.search.model;

import com.bob.springboot.country.search.enums.Clause;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public class SearchField {

    private String fieldName;               //字段名
    private Object value;                   //字段值
    private Clause clause = Clause.should;  //搜索条件
    private Type type = Type.String;        //字段类型
    private Boolean matchAll = false;       //允许为空查询所有

    public SearchField() {}

    public SearchField(String fieldName, Object value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    public SearchField(String fieldName, Object value, Boolean matchAll) {
        this.fieldName = fieldName;
        this.value = value;
        this.matchAll = matchAll;
    }

    public SearchField(String fieldName, Object value, Clause clause, Type type, Boolean matchAll) {
        this.fieldName = fieldName;
        this.value = value;
        this.clause = clause;
        this.type = type;
        this.matchAll = matchAll;
    }

    public enum Type {
        Number,
        String;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getMatchAll() {
        return matchAll;
    }

    public void setMatchAll(Boolean matchAll) {
        this.matchAll = matchAll;
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
                ", clause=" + clause +
                ", type=" + type +
                ", matchAll=" + matchAll +
                '}';
    }
}
