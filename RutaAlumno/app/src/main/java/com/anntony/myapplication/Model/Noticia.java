package com.anntony.myapplication.Model;

/**
 * Created by Antonio Facundo on 05/02/2018.
 */

import java.util.HashMap;
import java.util.Map;

public class Noticia {

    private String _id;
    private String name;
    private String content;
    private String category;
    private String date;
    private Integer v;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}