package com.bob.springboot.search;

import com.bob.springboot.search.constants.SearchConstants;
import com.bob.springboot.search.enums.Clause;
import com.bob.springboot.search.enums.QueryType;
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
import org.elasticsearch.search.highlight.HighlightBuilder;
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
            log.info("search [" + search.getURI() + "]: " + search.getData(gson));
            JestResult result = clientComponent.execute(search);
            if (result != null && result.isSucceeded()) {
                log.info("result : " + result.getJsonString());
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

            if (QueryType.match_all.equals(field.getType()))
                queryBuilder = QueryBuilders.matchAllQuery();

            if (QueryType.term.equals(field.getType()))
                queryBuilder = QueryBuilders.termQuery(field.getFieldName(), field.getValue());

            if (QueryType.match.equals(field.getType()))
                queryBuilder = QueryBuilders.matchQuery(field.getFieldName(), field.getValue());

            if (QueryType.match_phrase.equals(field.getType()))
                queryBuilder = QueryBuilders.matchPhraseQuery(field.getFieldName(), field.getValue());

            if (QueryType.multi_match.equals(field.getType())) {
                String names = field.getFieldName();
                if (names.indexOf(",") > 0)
                    queryBuilder = QueryBuilders.multiMatchQuery(field.getValue(), names.split(","));
            }

            if (QueryType.prefix.equals(field.getType())) {
                String value = field.getValue() != null ? field.getValue().toString() : null;
                queryBuilder = QueryBuilders.prefixQuery(field.getFieldName(), value);
            }

            if (field.getValue() != null && StringUtils.isNotBlank(field.getValue().toString())) {
                String value = field.getValue().toString();
                if (QueryType.query_string.equals(field.getType()))
                    queryBuilder = QueryBuilders.queryStringQuery(value)
                            .defaultField(field.getFieldName());

                if (QueryType.wildcard.equals(field.getType()))
                    queryBuilder = QueryBuilders.wildcardQuery(field.getFieldName(), value);

                if (QueryType.regexp.equals(field.getType()))
                    queryBuilder = QueryBuilders.regexpQuery(field.getFieldName(), value);
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

        if (request.getHighlight() != null && request.getHighlight().getHighlight()) {
            SearchRequest.Highlight highlight = request.getHighlight();
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field(highlight.getHighlightFieldName());
            highlightBuilder.preTags(highlight.getPreTag());
            highlightBuilder.postTags(highlight.getPostTag());
            builder.highlight(highlightBuilder);
        }
        return builder.query(query);
    }
}
