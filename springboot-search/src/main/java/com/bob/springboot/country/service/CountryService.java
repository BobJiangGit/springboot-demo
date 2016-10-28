package com.bob.springboot.country.service;

import com.bob.springboot.country.model.Country;

import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
public interface CountryService {

    List<Country> searchCountryList(Country country);

    Integer updateCountry(Country country);

    Integer saveCountry(Country country);
}
