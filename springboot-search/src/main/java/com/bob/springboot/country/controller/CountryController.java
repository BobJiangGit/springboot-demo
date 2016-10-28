package com.bob.springboot.country.controller;

import com.bob.springboot.country.model.Country;
import com.bob.springboot.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping("/search")
    public List<Country> search(Country country) {
        return countryService.searchCountryList(country);
    }
}
