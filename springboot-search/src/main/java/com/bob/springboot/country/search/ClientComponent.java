package com.bob.springboot.country.search;

import com.bob.springboot.country.search.prop.ESProp;
import com.bob.springboot.country.search.util.ApplicationContextUtil;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

/**
 * Created by Bob Jiang on 2016/10/28.
 */
public enum ClientComponent {

    INSTANCE;

    private JestClient jestClient;

    public JestClient getJestClient() {
        if (jestClient == null) {
            ESProp esProp = ApplicationContextUtil.getBean(ESProp.class);
            System.out.println(esProp.toString());
            HttpClientConfig clientConfig = new HttpClientConfig.Builder(esProp.getHosts())
                    .readTimeout(esProp.getReadTimeout()).connTimeout(esProp.getReadTimeout())
                    .multiThreaded(true).build();
            JestClientFactory factory = new JestClientFactory();
            factory.setHttpClientConfig(clientConfig);
            jestClient = factory.getObject();
        }
        return jestClient;
    }

    public void closeJestClient(){
        if (jestClient != null) {
            jestClient.shutdownClient();
            jestClient = null;
        }
    }
}
