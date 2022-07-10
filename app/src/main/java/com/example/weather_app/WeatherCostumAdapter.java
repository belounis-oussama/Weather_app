package com.example.weather_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeatherCostumAdapter extends RecyclerView.Adapter<WeatherCostumAdapter.MyViewHolder>{

    private Context context;
    private List<WeatherModel> weather;

    public WeatherCostumAdapter(Context context, List<WeatherModel> weather) {
        this.context = context;
        this.weather = weather;
    }

    @NonNull
    @Override
    public WeatherCostumAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.weatheritem,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherCostumAdapter.MyViewHolder holder, int position) {



        WeatherModel weatherModel=weather.get(position);

        holder.temp.setText(weatherModel.getTempreture());
        holder.windSpeed.setText(weatherModel.getWindSpeed());
        holder.time.setText(weatherModel.getTime());



    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView windSpeed,temp,time;
        private ImageView condition;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            windSpeed=itemView.findViewById(R.id.itemspeedwind);
            temp=itemView.findViewById(R.id.itemtemp);
            time=itemView.findViewById(R.id.itemtime);
            condition=itemView.findViewById(R.id.condition);
        }
    }
}
