package com.demo.webdemo.entity;


/**
 * Created by wyy on 2019/3/11 0011.
 */

public class EventBusDao {
    private String name;
    private int age;
    private String sex;

    public EventBusDao(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
