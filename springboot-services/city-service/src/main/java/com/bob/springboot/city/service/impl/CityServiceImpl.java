package com.bob.springboot.city.service.impl;

import com.bob.springboot.city.mapper.CityMapper;
import com.bob.springboot.city.model.City;
import com.bob.springboot.city.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/21.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<City> findCityList() {
        return cityMapper.findAll(null);
    }
}
