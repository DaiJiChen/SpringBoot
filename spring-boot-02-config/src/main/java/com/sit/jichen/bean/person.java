package com.sit.jichen.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/*
put value in configuration file to this class
use @ConfigurationProperties, tell springBoot taht all values in this class is configured in configuration file
prefix = "person", tell springBoot use which block of configuration file.

@Component, only components that are belong to the container can use @ConfigurationProperties functiooon provided by container
 */
@Component
@ConfigurationProperties(prefix = "person")
public class person {

    //@Value("${person.name}")
    private String name;
    //@Value("true")
    private Boolean boss;
    //@Value("#{11*3}")
    private int age;
    private Date birth;

    private Map<String, Object> map;
    private List<Object> list;
    private Dog dog;

    @Override
    public String toString() {
        return "person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", boss=" + boss +
                ", birth=" + birth +
                ", maps=" + map +
                ", list=" + list +
                ", dog=" + dog +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String ame) {
        this.name = ame;
    }

    public Boolean getBoss() {
        return boss;
    }

    public void setBoss(Boolean boss) {
        this.boss = boss;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> maps) {
        this.map = maps;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
