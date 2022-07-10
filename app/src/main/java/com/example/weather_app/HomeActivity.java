package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity implements LocationListener {


    TextView citynamev,tempv,conditionv;
    EditText citynametext;
    ImageView backgroud,icon,searchicon;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layouyt;
    List<WeatherModel> weatherForcaste;
    LocationManager locationManager;
    TextView timetext;
    private WeatherAdapter adapter;
    private ArrayList<WeatherModel> weatherModelArrayList;


    RelativeLayout weatherhome;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        initWidget();



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();





        weatherForcaste = new ArrayList<>();




        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager1);


        WeatherCostumAdapter adaptor1 = new WeatherCostumAdapter(this,weatherForcaste);
        recyclerView.setAdapter(adaptor1);



    }

    private void initWidget() {
        citynamev=findViewById(R.id.cityname);
        tempv=findViewById(R.id.Temp);
        conditionv=findViewById(R.id.contdiontext);
        citynametext=findViewById(R.id.citytext);
        recyclerView=findViewById(R.id.forecastweather);
        icon=findViewById(R.id.iconweather);
        weatherhome=findViewById(R.id.weatherhome);
        progressBar=findViewById(R.id.ProgressBar);
        timetext=findViewById(R.id.timetext);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            /*
            tvCity.setText(addresses.get(0).getLocality());
            tvState.setText(addresses.get(0).getAdminArea());
            tvCountry.setText(addresses.get(0).getCountryName());
            tvPin.setText(addresses.get(0).getPostalCode());
            tvLocality.setText(addresses.get(0).getAddressLine(0));
            */


            Toast.makeText(this,addresses.get(0).getLocality(), Toast.LENGTH_SHORT).show();



            String city=addresses.get(0).getLocality();






            String link="https://api.openweathermap.org/data/2.5/forecast?lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&appid=7b77e2f70d3bf662b1941efa517c5a7a&cnt=40&units=metric";


            String key="7b77e2f70d3bf662b1941efa517c5a7a";
            RequestQueue queue= Volley.newRequestQueue(HomeActivity.this);
            //String link="https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=7b77e2f70d3bf662b1941efa517c5a7a";
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {


                        progressBar.setVisibility(View.GONE);
                        weatherhome.setVisibility(View.VISIBLE);
                        //Toast.makeText(MainActivity.this,response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp"), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(MainActivity.this,response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"), Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(MainActivity.this,response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon"), Toast.LENGTH_SHORT).show();

                        String iconString =response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
                        //Toast.makeText(MainActivity.this,iconString, Toast.LENGTH_SHORT).show();

                        citynamev.setText(city);
                        //Toast.makeText(HomeActivity.this,response.getJSONArray("list").getJSONObject(0).getJSONObject("wind").getString("speed"), Toast.LENGTH_SHORT).show();

                        String iconlink="https://openweathermap.org/img/wn/"+iconString+"@4x.png";




                        Picasso.get().load(iconlink).into(icon);
                        int tempS=response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("temp");
                        tempv.setText(String.valueOf(tempS));
                        String condition=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                        conditionv.setText(condition);





                        String date=response.getJSONArray("list").getJSONObject(0).getString("dt_txt");
                        String time=date.substring(11,16);


                        timetext.setText(date.substring(0,11));

                        int tempp0=response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("temp");
                        String weatherdes=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                        String iconname=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
                        String windspeed=response.getJSONArray("list").getJSONObject(0).getJSONObject("wind").getString("speed");


                        for (int i=0;i<9;i++)
                        {
                            String datei=response.getJSONArray("list").getJSONObject(i).getString("dt_txt");
                            String timei=datei.substring(11,16);
                            int tempp=response.getJSONArray("list").getJSONObject(i).getJSONObject("main").getInt("temp");
                            //String weatherdes=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                            String iconnamei=response.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                            String windspeedi=response.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getString("speed");  //meter/sec
                            weatherForcaste.add(new WeatherModel(timei,String.valueOf(tempp)+" °C",iconnamei,windspeedi+"m/s"));

                        }




                        //Toast.makeText(HomeActivity.this,windspeed, Toast.LENGTH_SHORT).show();

                        //weatherForcaste.add(new WeatherModel(time,String.valueOf(tempp0),iconname,windspeed));




                    } catch (JSONException e) {
                        e.printStackTrace();


                        Toast.makeText(HomeActivity.this, "no answer", Toast.LENGTH_SHORT).show();
                    }

                    //conditionv.setText(response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(HomeActivity.this,"error"+error.toString(), Toast.LENGTH_SHORT).show();

                }
            });


            queue.add(request);


        } catch (Exception e) {
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);

    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }




    private void locationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(HomeActivity.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location to show Near Places Weather and forecast.")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, (LocationListener) this);
        } catch (SecurityException e) {
            Toast.makeText(this, "failed to get location", Toast.LENGTH_SHORT).show();
            getLocation();
            e.printStackTrace();
        }
    }
}