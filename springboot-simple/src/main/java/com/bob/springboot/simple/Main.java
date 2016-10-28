package com.bob.springboot.simple;

import com.bob.springboot.search.ClientComponent;
import com.bob.springboot.simple.prop.SearchProp;
import com.bob.springboot.simple.util.ApplicationContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by Bob Jiang on 2016/10/11.
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        ApplicationContextUtil.setApplicationContext(context);
        ClientComponent.INSTANCE.setSearchProp(ApplicationContextUtil.getBean(SearchProp.class));
    }

}
