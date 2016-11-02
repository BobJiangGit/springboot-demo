package com.bob.springboot.search;

import com.bob.springboot.search.constants.SearchConstants;
import com.bob.springboot.search.enums.Clause;
import com.bob.springboot.search.enums.QueryType;
import com.bob.springboot.search.model.SearchOrder;
import com.bob.springboot.search.model.SearchField;
import com.bob.springboot.search.model.SearchRequest;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public enum SearchComponent {

    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(SearchComponent.class);

    private ClientComponent clientComponent = ClientComponent.INSTANCE;

    public Search getSearch(SearchSourceBuilder searchSourceBuilder,
            String indexName, List<String> indexType, Integer from, Integer size) {
        Search.Builder builder = new Search.Builder(searchSourceBuilder.from(from).size(size).toString());
        builder.addIndex(indexName);
        builder.addType(indexType);
        return builder.build();
    }

    public JestResult search(SearchRequest request) {
        try {
            Long begin = System.currentTimeMillis();
            SearchSourceBuilder searchSourceBuilder = buildSearchBuilder(request);
            Integer size = request.getPageSize() != null ? request.getPageSize() : SearchConstants.SEARCH_PAGE_SIZE;
            Integer from = request.getPageNo() != null ? (request.getPageNo() - 1) * size : 0;
            Search search = getSearch(searchSourceBuilder, request.getIndexName(),
                    request.getIndexType(), from, size);
            log.info("search [" + search.getURI() + "]: " + search.getData(new Gson()));
            JestResult result = clientComponent.execute(search);
            if (result != null && result.isSucceeded()) {
                Long end = System.currentTimeMillis();
                log.info("result [" + (end - begin) + "ms] [" + result.getPathToResult() + "]: " + result.getJsonString());
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("search failed! " + e.getMessage(), e);
        }
    }

    public <T> List<T> searchList(SearchRequest request, Class<T> clazz) {
        JestResult result = search(request);
        List<T> list = new ArrayList<T>();
        if (result != null) {
            if (request.getHighlight() != null && request.getHighlight().getHighlight()) {
                Gson gson = new Gson();
                Map map = (Map) result.getValue(SearchConstants.SEARCH_RESULT_KEY_HITS);
                List<Map> hitsList = (List<Map>) map.get(SearchConstants.SEARCH_RESULT_KEY_HITS);
                for (Map hit : hitsList) {
                    String fieldName = request.getHighlight().getHighlightFieldName();
                    Map sourceMap = (Map) hit.get(SearchConstants.SEARCH_RESULT_KEY_SOURCE);
                    Map highMap = (Map) hit.get(SearchConstants.SEARCH_RESULT_KEY_HIGHLIGHT);
                    String value = "";
                    List valueList = (List) highMap.get(fieldName);
                    for (Object val : valueList)
                        value += val.toString();
                    if (!"".equals(value))
                        sourceMap.put(fieldName, value);
                    T t = gson.fromJson(mapToJson(sourceMap), clazz);
                    list.add(t);
                }
            } else
                list = result.getSourceAsObjectList(clazz);
        }
        return list;
    }

    public static String mapToJson(Map map) {
        StringBuilder json = new StringBuilder();
        if (map != null && map.size() > 0) {
            json.append("{");
            for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry e = (Map.Entry) it.next();
                json.append("\"").append(e.getKey()).append("\":\"").append(e.getValue()).append("\",");
            }
            String val = json.substring(0, json.lastIndexOf(",")) + "}";
            return val;
        }
        return null;
    }

    public <T> Map<String, Object> searchMap(SearchRequest request, Class<T> clazz) {
        Map<String, Object> map = Maps.newHashMap();
        JestResult result = search(request);
        if (result != null) {
            List<T> list = result.getSourceAsObjectList(clazz);
            map.put(SearchConstants.SEARCH_RESULT_KEY_RESULT, list);
            Map hitsMap = (Map) result.getValue(SearchConstants.SEARCH_RESULT_KEY_HITS);
            Number total = (Number) hitsMap.get(SearchConstants.SEARCH_RESULT_KEY_TOTAL);
            map.put(SearchConstants.SEARCH_RESULT_KEY_TOTAL, total.intValue());
        }
        return map;
    }

    public SearchSourceBuilder buildSearchBuilder(SearchRequest request) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        List<SearchField> fields = request.getField();
        if (fields == null || fields.size() == 0)
            throw new RuntimeException("searchField can't be null");
        boolean light = false;
        for (SearchField field : fields) {
            QueryBuilder queryBuilder = null;
            Object value = field.getValue();

            if (QueryType.match_all.equals(field.getType()))
                queryBuilder = QueryBuilders.matchAllQuery();

            if (QueryType.prefix.equals(field.getType())) {
                String val = value != null ? value.toString().toLowerCase() : "";
                queryBuilder = QueryBuilders.prefixQuery(field.getFieldName(), val);
            }

            if (value != null && StringUtils.isNotBlank(value.toString())) {
                light = true;
                String val = value.toString().toLowerCase();
                if (QueryType.term.equals(field.getType()))
                    queryBuilder = QueryBuilders.termQuery(field.getFieldName(), val);

                if (QueryType.query_string.equals(field.getType()))
                    queryBuilder = QueryBuilders.queryStringQuery(val)
                            .defaultField(field.getFieldName());

                if (QueryType.match.equals(field.getType()))
                    queryBuilder = QueryBuilders.matchQuery(field.getFieldName(), val);

                if (QueryType.match_phrase.equals(field.getType()))
                    queryBuilder = QueryBuilders.matchPhraseQuery(field.getFieldName(), val);

                if (QueryType.multi_match.equals(field.getType())) {
                    String names = field.getFieldName();
                    if (names.indexOf(",") > 0)
                        queryBuilder = QueryBuilders.multiMatchQuery(val, names.split(","));
                }

                if (QueryType.wildcard.equals(field.getType()))
                    queryBuilder = QueryBuilders.wildcardQuery(field.getFieldName(), val);

                if (QueryType.regexp.equals(field.getType()))
                    queryBuilder = QueryBuilders.regexpQuery(field.getFieldName(), value.toString());
            }

            if (Clause.should.equals(field.getClause()))
                query.should(queryBuilder);
            if (Clause.must.equals(field.getClause()))
                query.must(queryBuilder);
            if (Clause.mustNot.equals(field.getClause()))
                query.mustNot(queryBuilder);
        }

        List<SearchOrder> orderList = request.getOrder();
        if (orderList != null && orderList.size() > 0) {
            for (SearchOrder order : orderList) {
                builder.sort(order.getName(), order.getSort());
            }
        }

        if (request.getHighlight() != null) {
            SearchRequest.Highlight highlight = request.getHighlight();
            if (!light)
                highlight.setHighlight(false);
            if (highlight.getHighlight()) {
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                highlightBuilder.field(highlight.getHighlightFieldName());
                highlightBuilder.preTags(highlight.getPreTag());
                highlightBuilder.postTags(highlight.getPostTag());
                builder.highlight(highlightBuilder);
            }
        }
        return builder.query(query);
    }
}
