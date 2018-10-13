package com.wind;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BeanObj {

    private String name;
    private int age;
    private List<String> list;

    private Date date;

    private Map<String, String> map;

    private List<JsonObj> objList;

    private BeanObj(){

    }


    public BeanObj(String name){
        this.name = name;
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

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<JsonObj> getObjList() {
        return objList;
    }

    public void setObjList(List<JsonObj> objList) {
        this.objList = objList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
