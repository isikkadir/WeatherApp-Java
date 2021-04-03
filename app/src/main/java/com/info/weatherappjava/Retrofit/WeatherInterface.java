package com.info.weatherappjava.Retrofit;

import com.info.weatherappjava.Models.MyWeather;
import com.info.weatherappjava.Models.MyLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface WeatherInterface {
    @GET("api/location/search/?query=location")
    Call<List<MyLocation>> getLocation(
            @Query("query") String newLocation);

    @GET("api/location/{woeidID}/")
    Call<MyWeather> getWeather(
            @Path("woeidID") int woeidId
    );
}
