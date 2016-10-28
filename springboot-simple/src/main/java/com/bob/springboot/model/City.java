package com.bob.springboot.model;


import com.bob.base.model.BaseModel;

import java.io.Serializable;

/**
 * Created by Bob Jiang on 2016/10/12.
 */
public class City extends BaseModel implements Serializable {

    private static final long serialVersionUID = 783058839816825226L;

    private String name;

    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
