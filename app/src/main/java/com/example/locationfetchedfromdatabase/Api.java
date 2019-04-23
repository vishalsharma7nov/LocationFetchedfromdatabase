package com.example.locationfetchedfromdatabase;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL="http://api.hostingfunda.com/tracking/";
    @GET("Location.php")
    Call<msg> getHeroes();
}