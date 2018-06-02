package com.anntony.myapplication.Model;

/**
 * Created by Antonio Facundo on 05/02/2018.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Noticias {

    private List<Noticia> noticias = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

