package com.bob.springboot.search;

import com.bob.springboot.search.constants.SearchConstants;
import com.bob.springboot.search.enums.Clause;
import com.bob.springboot.search.model.SearchOrder;
import com.bob.springboot.search.model.SearchField;
import com.bob.springboot.search.model.SearchRequest;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            Gson gson = new Gson();

            SearchSourceBuilder searchSourceBuilder = buildSearchBuilder(request);
            Integer from = (request.getPageNo() - 1) * request.getPageSize();
            Search search = getSearch(searchSourceBuilder, request.getIndexName(),
                    request.getIndexType(), from, request.getPageSize());
            log.info("search :" + gson.toJson(search));
            log.info("searchSourceBuilder :" + gson.toJson(searchSourceBuilder));
            JestResult result = clientComponent.execute(search);
            if (result != null && result.isSucceeded()) {
                log.info("result:" + result.getJsonString());
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("search failed! " + e.getMessage(), e);
        }
    }

    public <T> List<T> searchList(SearchRequest request, Class<T> clazz) {
        JestResult result = search(request);
        return result != null ? result.getSourceAsObjectList(clazz) : null;
    }

    public <T> Map<String, Object> searchMap(SearchRequest request, Class<T> clazz) {
        Map<String, Object> map = Maps.newHashMap();
        JestResult result = search(request);
        if (result != null) {
            List<T> list = result.getSourceAsObjectList(clazz);
            map.put(SearchConstants.SEARCH_RESULT_MAPKEY, list);
            Map hitsMap = (Map) result.getValue(SearchConstants.SEARCH_RESULT_HITS);
            if (hitsMap != null && hitsMap.size() > 0) {
                if (hitsMap.get(SearchConstants.SEARCH_RESULT_TOTAL) != null) {
                    Number total = (Number) hitsMap.get(SearchConstants.SEARCH_RESULT_TOTAL);
                    map.put(SearchConstants.SEARCH_RESULT_TOTAL, total.intValue());
                }
            }
        }
        return map;
    }

    public SearchSourceBuilder buildSearchBuilder(SearchRequest request) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        List<SearchField> fields = request.getField();
        if (fields == null || fields.size() == 0)
            throw new RuntimeException("searchField can't be null");
        for (SearchField field : fields) {
            QueryBuilder queryBuilder = null;
            if (field.getValue() != null && StringUtils.isNotBlank(field.getValue().toString())) {
                if (SearchField.Type.String.equals(field.getType())) {
                    queryBuilder = QueryBuilders.queryStringQuery(field.getValue().toString())
                            .defaultField(field.getFieldName());
                } else {
                    queryBuilder = QueryBuilders.termQuery(field.getFieldName(), field.getValue());
                }
            } else
                if (field.getMatchAll())
                    queryBuilder = QueryBuilders.matchAllQuery();

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
        return builder.query(query);
    }
}
