package com.bob.springboot.simple.controller;

import com.bob.springboot.simple.model.City;
import com.bob.springboot.simple.model.response.ResultResponse;
import com.bob.springboot.simple.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/21.
 */
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping("/index")
    public String index() {
        return "hello world";
    }

    @RequestMapping("/user/{userName}")
    public String post(@PathVariable("userName") String userName) {
        return String.format("hello, user %s, haha.", userName);
    }

    @RequestMapping("/findList")
    public ResultResponse<?> findCityList() {
        try {
            List<City> list = cityService.findCityList();
            return ResultResponse.success(list);
        } catch (Exception e) {
            return ResultResponse.failed();
        }
    }
}
