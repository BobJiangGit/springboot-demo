package com.bob.springboot.search;

import com.bob.springboot.search.prop.SearchProp;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

/**
 * Created by Bob Jiang on 2016/10/28.
 */
public enum ClientComponent {

    INSTANCE;
    private SearchProp searchProp;
    private JestClient jestClient;

    public JestClient getJestClient() {
        if (jestClient == null) {
            if (searchProp == null)
                throw new RuntimeException("searchProp can't be null");
            HttpClientConfig clientConfig = new HttpClientConfig.Builder(searchProp.getHosts())
                    .readTimeout(searchProp.getReadTimeout()).connTimeout(searchProp.getReadTimeout())
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

    public void setSearchProp(SearchProp searchProp) {
        this.searchProp = searchProp;
    }
}
