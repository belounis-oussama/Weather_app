package com.example.weather_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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
        holder.humidity.setText(weatherModel.getHumidity()+" %");
       // int iconDrwbl=getWeatherIcon(weatherModel.getIcon());
       // Toast.makeText(context, weatherModel.getIcon(), Toast.LENGTH_SHORT).show();
       // holder.condition.setBackgroundResource(iconDrwbl);
        //holder.condition.setImageDrawable(holder.itemView.getResources().getDrawable(iconDrwbl));

       // holder.condition.setImageResource(R.drawable.clearskyday);
     String iconlink="https://openweathermap.org/img/wn/"+weatherModel.getIcon()+"@4x.png";

      Picasso.get().load(iconlink).into(holder.condition);



    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView windSpeed,temp,time,humidity;
        private ImageView condition;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            windSpeed=itemView.findViewById(R.id.itemspeedwind);
            temp=itemView.findViewById(R.id.itemtemp);
            time=itemView.findViewById(R.id.itemtime);
            condition=itemView.findViewById(R.id.condition);
            humidity=itemView.findViewById(R.id.itemhumidity);
        }
    }

    private int getWeatherIcon(String iconString) {
        int drwblresult=R.drawable.clearskyday;

        if (iconString.equals("01d")) drwblresult=R.drawable.clearskyday;
        if (iconString.equals("01n")) drwblresult=R.drawable.clearskynight;
        if (iconString.equals("02d")) drwblresult=R.drawable.fewcloudsday;
        if (iconString.equals("02n")) drwblresult=R.drawable.fewcloudsnight;
        if (iconString.equals("03d")) drwblresult=R.drawable.scattered_cloudsnd;
        if (iconString.equals("03n")) drwblresult=R.drawable.scattered_cloudsnd;
        if (iconString.equals("04d")) drwblresult=R.drawable.brokencloudsdn;
        if (iconString.equals("04n")) drwblresult=R.drawable.brokencloudsdn;
        if (iconString.equals("09d")) drwblresult=R.drawable.showerraindn;
        if (iconString.equals("09n")) drwblresult=R.drawable.showerraindn;
        if (iconString.equals("10d")) drwblresult=R.drawable.rain_day;
        if (iconString.equals("10n")) drwblresult=R.drawable.rain_night;
        if (iconString.equals("11d")) drwblresult=R.drawable.thunderstromdn;
        if (iconString.equals("11n")) drwblresult=R.drawable.thunderstromdn;
        if (iconString.equals("13d")) drwblresult=R.drawable.snowdn;
        if (iconString.equals("13n")) drwblresult=R.drawable.snowdn;
        if (iconString.equals("50d")) drwblresult=R.drawable.mist_day;
        if (iconString.equals("50n")) drwblresult=R.drawable.mist_night;

        return drwblresult;
    }
}
