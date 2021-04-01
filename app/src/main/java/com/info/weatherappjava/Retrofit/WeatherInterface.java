package com.info.weatherappjava.Retrofit;

import com.info.weatherappjava.PojoModels.MyWeather;
import com.info.weatherappjava.PojoModels.myLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface WeatherInterface {
    @GET("location/search/?query=location")
    Call<List<myLocation>> getLocation(
            @Query("query") String newLocation);

    @GET("location/{woeidID}/")
    Call<MyWeather> getWeather(
            @Path("woeidID") int woeidId
    );
}
