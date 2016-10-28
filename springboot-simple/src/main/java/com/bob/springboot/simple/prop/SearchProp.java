package com.bob.springboot.simple.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Bob Jiang on 2016/10/28.
 */
@Component
@ConfigurationProperties(prefix = "esServer")
public class SearchProp extends com.bob.springboot.search.prop.SearchProp {
}
