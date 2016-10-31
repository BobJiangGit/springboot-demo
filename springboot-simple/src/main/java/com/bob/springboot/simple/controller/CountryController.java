package com.bob.springboot.simple.controller;

import com.bob.springboot.simple.model.Country;
import com.bob.springboot.simple.model.response.ResultResponse;
import com.bob.springboot.simple.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    @RequestMapping("/search")
    public ResultResponse<?> search(Country country, Integer pageNo, Integer pageSize) {
        try {
            List<Country> list = countryService.searchCountryList(country, pageNo, pageSize);
            return ResultResponse.success(list);
        } catch (Exception e) {
            log.error("search failed!", e);
            return ResultResponse.failed("search failed!");
        }
    }

    @RequestMapping("/initIndex")
    public ResultResponse<?> initIndex(Country country) {
        try {
            countryService.initCountryIndex();
            return ResultResponse.success();
        } catch (Exception e) {
            log.error("init Index failed!", e);
            return ResultResponse.failed("init Index failed!");
        }
    }

}
