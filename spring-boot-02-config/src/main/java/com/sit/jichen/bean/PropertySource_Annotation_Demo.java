package com.sit.jichen.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@PropertySource(value = {"classpath:demo.yml"})
@Component
@ConfigurationProperties(prefix = "demo")
public class PropertySource_Annotation_Demo {
    private String name;
    private Boolean boss;
    private int age;
    private Date birth;

    private Map<String, Object> map;
    private List<Object> list;
    private Dog dog;

    @Override
    public String toString() {
        return "demo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", boss=" + boss +
                ", birth=" + birth +
                ", maps=" + map +
                ", list=" + list +
                ", dog=" + dog +
                '}';
    }
}
