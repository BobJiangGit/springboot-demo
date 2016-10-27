package com.bob.springboot.country.search;


import com.bob.springboot.country.search.prop.ESProp;
import io.searchbox.client.JestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
@Component
public class ESIndexComponent {

    @Autowired
    private ESProp esProp;

    private JestClient jestClient;


}
