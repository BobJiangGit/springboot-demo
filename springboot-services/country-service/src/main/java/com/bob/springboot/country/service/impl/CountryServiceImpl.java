package com.bob.springboot.country.service.impl;

import com.bob.springboot.country.mapper.CountryMapper;
import com.bob.springboot.country.model.Country;
import com.bob.springboot.country.search.ESSearchComponent;
import com.bob.springboot.country.search.constants.SearchConstants;
import com.bob.springboot.country.search.model.SearchField;
import com.bob.springboot.country.search.model.SearchRequest;
import com.bob.springboot.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private ESSearchComponent searchComponent;

    @Override
    public List<Country> searchCountryList(Country country) {
        SearchRequest request = new SearchRequest();
        request.setIndexName(SearchConstants.COUNTRY_INDEX);
        request.setIndexType(SearchConstants.COUNTRY_INDEX_TYPE);
        request.setSearchField(new ArrayList<SearchField>(){{
            Object value = (country != null) ? country.getCountryname() : null;
            add(new SearchField("countryname", value, true));
        }});
//        request.setOrder(new ArrayList<ESOrder>(){{
//            add(new ESOrder("countryname"));
//        }});
        return searchComponent.searchList(request, Country.class);
    }

    @Override
    public Integer updateCountry(Country country) {
        return countryMapper.update(country);
    }

    @Override
    public Integer saveCountry(Country country) {
        return countryMapper.save(country);
    }

}
