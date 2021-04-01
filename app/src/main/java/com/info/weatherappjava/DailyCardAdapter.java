package com.info.weatherappjava;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.weatherappjava.PojoModels.ConsolidatedWeather;
import com.info.weatherappjava.PojoModels.MyWeather;

import java.util.ArrayList;
import java.util.List;

public class DailyCardAdapter extends RecyclerView.Adapter<DailyCardAdapter.dailyCardHolder>{
    //MyWeather weather;
    //List<ConsolidatedWeather> consolidatedWeathers = weather.getConsolidatedWeather();
    List<ConsolidatedWeather> consolidatedWeatherList = new ArrayList<>();

    LayoutInflater inflater;

    public DailyCardAdapter(Activity activity, List<ConsolidatedWeather> consolidatedWeatherList) {
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.consolidatedWeatherList = consolidatedWeatherList;
    }

    @NonNull
    @Override
    public dailyCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.consolidated_weather_card,null);
        dailyCardHolder dailyCardHolder = new dailyCardHolder(cardView);
        return dailyCardHolder;
    }

    @Override
    public int getItemCount() {
        return consolidatedWeatherList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull dailyCardHolder holder, int position) {
        ConsolidatedWeather consolidatedWeatherTemp= consolidatedWeatherList.get(position);
        holder.consolidated_day.setText("Gün " + position);
        holder.consolidated_maxTemp.setText(String.valueOf(consolidatedWeatherTemp.getMaxTemp()));
        holder.consolidated_minTemp.setText(String.valueOf(consolidatedWeatherTemp.getMinTemp()));
        holder.consolidated_temp.setText(String.valueOf(consolidatedWeatherTemp.getTheTemp()));

        holder.consolidated_abbr.setImageResource(R.drawable.search);
        holder.consolidated_maxAbbr.setImageResource(R.drawable.search);
        holder.consolidated_minAbbr.setImageResource(R.drawable.search);
    }

    class dailyCardHolder extends RecyclerView.ViewHolder{
        TextView consolidated_day,consolidated_temp,consolidated_minTemp,consolidated_maxTemp;
        ImageView consolidated_abbr,consolidated_minAbbr,consolidated_maxAbbr;

        public dailyCardHolder(@NonNull View itemView) {
            super(itemView);
            consolidated_day= itemView.findViewById(R.id.consolidated_day);
            consolidated_temp= itemView.findViewById(R.id.consolidated_temp);
            consolidated_minTemp= itemView.findViewById(R.id.consolidated_minTemp);
            consolidated_maxTemp= itemView.findViewById(R.id.consolidated_maxTemp);
            consolidated_abbr= itemView.findViewById(R.id.consolidated_abbr);
            consolidated_minAbbr= itemView.findViewById(R.id.consolidated_minAbbr);
            consolidated_maxAbbr= itemView.findViewById(R.id.consolidated_maxAbbr);
        }
    }
}
