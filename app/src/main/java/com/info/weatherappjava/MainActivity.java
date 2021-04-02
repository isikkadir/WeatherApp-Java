package com.info.weatherappjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.info.weatherappjava.PojoModels.MyWeather;
import com.info.weatherappjava.PojoModels.myLocation;
import com.info.weatherappjava.Retrofit.ApiClient;
import com.info.weatherappjava.Retrofit.WeatherInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private TextView city_name,temp,state,humidity,wind;
    private WeatherInterface Iweather;
    private String aranacakSehirAdi;
    private Toolbar toolbar;
    private RecyclerView rv;
    private ImageView abbr;
    private DailyCardAdapter adapter;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        toolbarDesign();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }
    private void toolbarDesign() {
        toolbar.setTitle("Weather App");
        setSupportActionBar(toolbar);
    }
    private void init() {
        city_name = findViewById(R.id.city_name);
        Iweather = ApiClient.getClient().create(WeatherInterface.class);
        toolbar = findViewById(R.id.toolbar);
        temp = findViewById(R.id.temp);
        state = findViewById(R.id.state);
        humidity = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind);
        rv = findViewById(R.id.rv);
        abbr = findViewById(R.id.abbr);

    }
    private void getLocation(){

       Iweather.getLocation(aranacakSehirAdi).enqueue(new Callback<List<myLocation>>() {

           @Override
           public void onResponse(Call<List<myLocation>> call, Response<List<myLocation>> response) {
               List<myLocation> lokasyon = response.body();
               int cityWoeidId = lokasyon.get(0).getWoeid();
               getWeather(cityWoeidId);
           }
           @Override
           public void onFailure(Call<List<myLocation>> call, Throwable t) {
           }
       });
    }
    private void getWeather(int cityWoeidId){
        Iweather.getWeather(cityWoeidId).enqueue(new Callback<MyWeather>() {
            @Override
            public void onResponse(Call<MyWeather> call, Response<MyWeather> response) {
                MyWeather weather = response.body();
                setData(weather);
            }
            @Override
            public void onFailure(Call<MyWeather> call, Throwable t) {
                Log.e("Hata" , "Mesaj: " + t.getMessage());
            }
        });

    }
    private void setData(MyWeather weather) {
        //set textViews
        state.setText(weather.getConsolidatedWeather().get(0).getWeatherStateName());
        temp.setText(String.valueOf(Math.round(weather.getConsolidatedWeather().get(0).getTheTemp())));
        city_name.setText(String.format("%s,%s", weather.getTitle(), weather.getParent().getTitle()));
        humidity.setText(String.format("Humidity: %s", Math.round(weather.getConsolidatedWeather().get(0).getHumidity())));
        wind.setText(String.format("Wind: %s", Math.round(weather.getConsolidatedWeather().get(0).getWindSpeed())));
        //set Adapter
        adapter = new DailyCardAdapter(this,weather.getConsolidatedWeather());
        //set image
        String url = "https://www.metaweather.com/static/img/weather/png/"+ weather.getConsolidatedWeather().get(0).getWeatherStateAbbr() + ".png";
        Picasso.get().load(url).into(abbr);
        //set recycleview
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv.setAdapter(adapter);

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        aranacakSehirAdi = query;
        getLocation();
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
//Log.e("Mesaj", "onQueryTextSubmit * " + aranacakSehirAdi);