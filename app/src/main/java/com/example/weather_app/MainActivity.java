package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    TextView citynamev,tempv,conditionv;
    EditText citynametext;
    ImageView backgroud,icon,searchicon;
    RecyclerView recyclerView;
    private ArrayList<WeatherModel> weatherModelArrayList;
    private WeatherAdapter adapter;
    private LocationManager locationManager;
    private int PERMISSION_CODE =1;
    private String cityNameString;
    RecyclerView.LayoutManager layouyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initWidget();


        String link="https://api.openweathermap.org/data/2.5/forecast?q=london&appid=7b77e2f70d3bf662b1941efa517c5a7a&cnt=3&units=metric";


        String key="7b77e2f70d3bf662b1941efa517c5a7a";
        RequestQueue queue=Volley.newRequestQueue(MainActivity.this);
        //String link="https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=7b77e2f70d3bf662b1941efa517c5a7a";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Toast.makeText(MainActivity.this,response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp"), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(MainActivity.this,response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"), Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(MainActivity.this,response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon"), Toast.LENGTH_SHORT).show();

                    String iconString =response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
                     //Toast.makeText(MainActivity.this,iconString, Toast.LENGTH_SHORT).show();

Toast.makeText(MainActivity.this,response.getJSONArray("list").getJSONObject(0).getJSONObject("wind").getString("speed"), Toast.LENGTH_SHORT).show();

                    String iconlink="https://openweathermap.org/img/wn/"+iconString+"@4x.png";




                    String n="";
                    Picasso.get().load(iconlink).into(icon);
                    String tempS=response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp");
                    tempv.setText(tempS);
                    String condition=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                    conditionv.setText(condition);





                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //conditionv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"error"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });


        queue.add(request);


        weatherModelArrayList.add(new WeatherModel("s","s","https://openweathermap.org/img/wn/02n@4x.png","s", "humidity"));

        recyclerView.setHasFixedSize(true);
        layouyt=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layouyt);
        adapter=new WeatherAdapter(this,weatherModelArrayList);

        recyclerView.setAdapter(adapter);





    }




    private String getCityname (double longitude, double latitude)
    {
        String cityName ="Not found";
        Geocoder gcd=new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses=gcd.getFromLocation(longitude,latitude,10);

            for (Address adr: addresses)
            {
                if (adr!=null)
                {
                    String city=adr.getLocality();
                    if (city!=null && !city.equals(""))
                    {
                        cityName=city;
                    }
                    else {
                        Log.d("TAG","CITY NOT FOUND");
                        Toast.makeText(this, "User City Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return cityName;
    }


    private void initWidget() {



    }

    private void getWeatherInfo(String cityName)
    {
        String url="http://api.weatherapi.com/v1/forecast.json?key=d7b0b1f093d84b9088401950220907&q="+cityName+"&days=1&aqi=yes&alerts=yes";
        citynamev.setText(cityName);
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                weatherModelArrayList.clear();

                try {
                    String temp= response.getJSONObject("current").getString("temp_c");
                    tempv.setText(temp+" °C");

                    int isDay =response.getJSONObject("current").getInt("is_day");
                    String condition=response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon=response.getJSONObject("current").getJSONObject("condition").getString("icon");

                    Picasso.get().load("http:".concat(conditionIcon)).into(icon);


                    conditionv.setText(condition);
                    if (isDay==1)
                    {

                    }
                    else {

                    }


                    JSONObject forecast= response.getJSONObject("forecast");
                    JSONObject forecast0=forecast.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray= forecast0.getJSONArray("hour");


                    for (int i=0;i<hourArray.length();i++)
                    {
                        JSONObject hourObject=hourArray.getJSONObject(i);
                        String time=hourObject.getString("time");
                        String temphour=hourObject.getString("temp_c");
                        String image=hourObject.getJSONObject("condition").getString("icon");
                        String windspeed=hourObject.getString("wind_kph");


                        weatherModelArrayList.add(new WeatherModel(time,temphour,image,windspeed, "humidity"));
                    }

                    adapter.notifyDataSetChanged();





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please enter a valid city name", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);


    }
}