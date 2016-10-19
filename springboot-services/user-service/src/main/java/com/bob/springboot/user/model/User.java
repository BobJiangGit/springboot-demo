package com.bob.springboot.user.model;


import com.bob.base.model.BaseModel;

import java.io.Serializable;

/**
 * Created by Bob Jiang on 2016/10/12.
 */
public class User extends BaseModel implements Serializable {

    private static final long serialVersionUID = 783058839816825226L;

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
