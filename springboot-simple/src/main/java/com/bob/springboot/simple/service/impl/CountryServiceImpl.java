package com.bob.springboot.simple.service.impl;

import com.bob.springboot.search.IndexComponent;
import com.bob.springboot.search.constants.SearchConstants;
import com.bob.springboot.search.SearchComponent;
import com.bob.springboot.search.enums.QueryType;
import com.bob.springboot.search.model.SearchOrder;
import com.bob.springboot.simple.mapper.CountryMapper;
import com.bob.springboot.simple.model.Country;
import com.bob.springboot.search.model.SearchField;
import com.bob.springboot.search.model.SearchRequest;
import com.bob.springboot.simple.service.CountryService;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public List<Country> searchCountryList(Country country, Integer pageNo, Integer pageSize) {
        SearchComponent searchComponent = SearchComponent.INSTANCE;
        SearchRequest request = new SearchRequest();
        request.setIndexName(SearchConstants.INDEX_SPRINGBOOT_DEMO);
        request.setOneIndexType(SearchConstants.INDEX_TYPE_COUNTRY);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        Object value = country != null ? country.getCountryName() : null;
//        request.setField(new ArrayList<SearchField>(){{
//            if (value != null && !"".equals(value)) {
//                add(new SearchField("countryName", value, QueryType.query_string));
//                add(new SearchField("countryName", value, QueryType.prefix));
//            } else
//                add(new SearchField("countryName", value, QueryType.match_all));
//        }});
        request.setOneField(new SearchField("countryName", value, QueryType.prefix));
        request.setOneOrder(new SearchOrder("id"));
        request.setHighlight(request.new Highlight("countryName"));
        return searchComponent.searchList(request, Country.class);
    }

    @Override
    public void initCountryIndex() {
        List<Country> list = findAll(null);
        IndexComponent.INSTANCE.saveDocList(SearchConstants.INDEX_SPRINGBOOT_DEMO,
                SearchConstants.INDEX_TYPE_COUNTRY, list);
    }

    @Override
    public Integer updateCountry(Country country) {
        return countryMapper.update(country);
    }

    @Override
    public Integer saveCountry(Country country) {
        return countryMapper.save(country);
    }

    @Override
    public List<Country> findAll(Country country) {
        return countryMapper.findAll(country);
    }

}
