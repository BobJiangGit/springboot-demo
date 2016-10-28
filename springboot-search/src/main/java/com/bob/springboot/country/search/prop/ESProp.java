package com.bob.springboot.country.search.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/27.
 */
@Component
@ConfigurationProperties(prefix="es.server")
public class ESProp {

    private List<String> hosts;
    private Integer readTimeout;
    private Integer connTimeout;

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(Integer connTimeout) {
        this.connTimeout = connTimeout;
    }

    @Override
    public String toString() {
        return "ESProp{" +
                "hosts=" + hosts +
                ", readTimeout=" + readTimeout +
                ", connTimeout=" + connTimeout +
                '}';
    }
}
