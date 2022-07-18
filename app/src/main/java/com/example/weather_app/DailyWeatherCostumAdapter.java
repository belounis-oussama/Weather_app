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

import java.util.List;

public class DailyWeatherCostumAdapter extends RecyclerView.Adapter<DailyWeatherCostumAdapter.MyViewHolder>{

    private Context context;
    private List<WeatherModelDaily> weather;

    public DailyWeatherCostumAdapter(Context context, List<WeatherModelDaily> weather) {
        this.context = context;
        this.weather = weather;
    }

    @NonNull
    @Override
    public DailyWeatherCostumAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.dailyweatheritem,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherCostumAdapter.MyViewHolder holder, int position) {



        WeatherModelDaily weatherModel=weather.get(position);

        holder.tempday.setText(weatherModel.getTempretureday());

        holder.windSpeed.setText(weatherModel.getWindSpeed());
        holder.day.setText(weatherModel.getDay());

        String iconlinkday="https://openweathermap.org/img/wn/"+weatherModel.getIconday()+"@4x.png";
        Picasso.get().load(iconlinkday).into(holder.conditionday);





    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView windSpeed,tempday,tempnight,day;
        private ImageView conditionday,conditionnight;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            windSpeed=itemView.findViewById(R.id.itemspeedwindd);
            tempday=itemView.findViewById(R.id.itemtempd);

            day=itemView.findViewById(R.id.day_text);
            conditionday=itemView.findViewById(R.id.conditiond);

        }
    }
}
