package com.bob.springboot.simple.service;

import com.bob.springboot.simple.model.Country;

import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public interface CountryService {

    List<Country> searchCountryList(Country country, Integer pageNo, Integer pageSize);

    void initCountryIndex();

    Integer updateCountry(Country country);

    Integer saveCountry(Country country);

    List<Country> findAll(Country country);
}
