package com.anntony.myapplication.Api;

import com.anntony.myapplication.Model.Noticia;
import com.anntony.myapplication.Model.Noticias;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Antonio Facundo on 05/02/2018.
 */

public interface APIService {

    @GET("noticias/")
    Call<Noticias> getNoticias();

    @GET("noticias/{id}/")
    Call<Noticia> getNoticia(@Path("id") String id);
}
