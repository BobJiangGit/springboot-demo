package com.bob.springboot.service;

import com.bob.springboot.model.City;

import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/21.
 */
public interface CityService {

    List<City> findCityList();

}
