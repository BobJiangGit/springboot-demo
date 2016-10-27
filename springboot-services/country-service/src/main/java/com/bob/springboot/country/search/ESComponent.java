package com.bob.springboot.country.search;

import com.bob.springboot.country.search.enums.Clause;
import com.bob.springboot.country.search.model.ESOrder;
import com.bob.springboot.country.search.model.SearchField;
import com.bob.springboot.country.search.model.SearchRequest;
import com.bob.springboot.country.search.prop.ESProp;
import com.google.common.collect.Maps;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
@Component
public class ESComponent {

//    INSTANCE;

    @Autowired
    private ESProp esProp;

    private JestClient jestClient;

    public JestClient getJestClient() {
        if (jestClient == null) {
            System.out.println("server : " + esProp.getHosts());

            HttpClientConfig clientConfig = new HttpClientConfig.Builder(esProp.getHosts())
                    .readTimeout(esProp.getReadTimeout()).connTimeout(esProp.getReadTimeout())
                    .multiThreaded(true).build();
            JestClientFactory factory = new JestClientFactory();
            factory.setHttpClientConfig(clientConfig);
            jestClient = factory.getObject();
        }
        System.out.println("get client");
        return jestClient;
    }

    public static Search getSearch(SearchSourceBuilder searchSourceBuilder,
            String indexName, String indexType, Integer from, Integer size) {
        Search.Builder builder = new Search.Builder(searchSourceBuilder.from(from).size(size).toString());
        builder.addIndex(indexName);
        builder.addType(indexType);
        return builder.build();
    }

    public JestResult search(SearchRequest request) {
        JestResult result = null;
        try {
            SearchSourceBuilder searchSourceBuilder = buildSearchBuilder(request);
            Integer from = (request.getPageNo() - 1) * request.getPageSize();
            Search search = getSearch(searchSourceBuilder, request.getIndexName(),
                    request.getIndexType(), from, request.getPageSize());
            jestClient = getJestClient();
            result = jestClient.execute(search);
        } catch (IOException e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }

    public <T> List<T> searchList(SearchRequest request, Class<T> clazz) {
        JestResult result = search(request);
        return result.getSourceAsObjectList(clazz);
    }

    public <T> Map<String, Object> searchMap(SearchRequest request, Class<T> clazz) {
        Map<String, Object> map = Maps.newHashMap();
        JestResult result = search(request);
        List<T> list = result.getSourceAsObjectList(clazz);
        map.put("list", list);
        Map hitsMap = (Map) result.getValue("hits");
        if(!CollectionUtils.isEmpty(hitsMap)){
            if (hitsMap.get("total") != null) {
                Number total = (Number) hitsMap.get("total");
                map.put("total", total.intValue());
            }
        }
        return map;
    }

    public SearchSourceBuilder buildSearchBuilder(SearchRequest request) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        List<SearchField> fields = request.getSearchField();
        if (!CollectionUtils.isEmpty(fields)) {
            for (SearchField field : fields) {
                QueryBuilder queryBuilder = null;

                if (field.getValue() != null || StringUtils.isNotBlank(field.getValue().toString())) {
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
        }

        List<ESOrder> orderList = request.getOrder();
        if (!CollectionUtils.isEmpty(orderList)) {
            for (ESOrder order : orderList) {
                builder.sort(order.getName(), order.getSort());
            }
        }
        return builder.query(query);
    }

    public void closeJestClient(){
        if (jestClient != null) {
            jestClient.shutdownClient();
            jestClient = null;
        }
    }
}
