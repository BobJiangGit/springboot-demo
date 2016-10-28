package com.bob.springboot.service.impl;

import com.bob.springboot.mapper.CityMapper;
import com.bob.springboot.model.City;
import com.bob.springboot.service.CityService;
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
