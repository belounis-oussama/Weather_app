package com.example.weather_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private Context context;
    private ArrayList <WeatherModel> weatherModelArrayList;

    public WeatherAdapter(Context context, ArrayList<WeatherModel> weatherModelArrayList) {
        this.context = context;
        this.weatherModelArrayList = weatherModelArrayList;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.weatheritem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {

        WeatherModel weatherModel=weatherModelArrayList.get(position);
        holder.windSpeed.setText(weatherModel.getWindSpeed()+" Km/h");
        holder.temp.setText(weatherModel.getTempreture()+" °C");


            holder.time.setText(weatherModel.getTime());


        Picasso.get().load(weatherModel.getIcon()).into(holder.condition);

    }

    @Override
    public int getItemCount() {
        return weatherModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{




        private TextView windSpeed,temp,time;
        private ImageView condition;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            windSpeed=itemView.findViewById(R.id.itemspeedwind);
            temp=itemView.findViewById(R.id.itemtemp);
            time=itemView.findViewById(R.id.itemtime);
            condition=itemView.findViewById(R.id.condition);




        }
    }
}
