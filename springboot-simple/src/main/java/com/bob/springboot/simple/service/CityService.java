package com.bob.springboot.simple.service;

import com.bob.springboot.simple.model.City;

import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/21.
 */
public interface CityService {

    List<City> findCityList();

}
