package com.anntony.myapplication.Api;

/**
 * Created by Antonio Facundo on 05/02/2018.
 */

public class APIUtilities {

    private APIUtilities() {}

    public static final String BASE_URL = "https://uteush.herokuapp.com/ut/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
