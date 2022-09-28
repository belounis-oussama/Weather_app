package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity implements LocationListener {


    TextView citynamev,tempv,conditionv,sunsetV;
    EditText citynametext;
    ImageView backgroud,icon,searchicon;
    RecyclerView recyclerView,recyclerViewdaily;

    List<WeatherModel> weatherForcaste;
    List<WeatherModelDaily> weatherForcastedaily;
    LocationManager locationManager;
    Toolbar toolbar;


    TextView sunrise;





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


        setSupportActionBar(toolbar);


        weatherForcaste = new ArrayList<>();
        weatherForcastedaily =new ArrayList<>();




        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager1);


        WeatherCostumAdapter adaptor1 = new WeatherCostumAdapter(this,weatherForcaste);
        recyclerView.setAdapter(adaptor1);



        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewdaily.setLayoutManager(manager2);



        DailyWeatherCostumAdapter adapter2=new DailyWeatherCostumAdapter(this,weatherForcastedaily);
        recyclerViewdaily.setAdapter(adapter2);








    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initWidget() {
        citynamev=findViewById(R.id.cityname);
        tempv=findViewById(R.id.Temp);
        conditionv=findViewById(R.id.contdiontext);
        sunrise=findViewById(R.id.sunrise);

        recyclerView=findViewById(R.id.forecastweather);
        recyclerViewdaily=findViewById(R.id.forecastweatherday);
        icon=findViewById(R.id.iconweather);
        weatherhome=findViewById(R.id.weatherhome);
        progressBar=findViewById(R.id.ProgressBar);
        sunsetV=findViewById(R.id.sunset);
        toolbar=findViewById(R.id.toolbar);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.settings)
        {
            startActivity(new Intent(HomeActivity.this, com.example.weather_app.Settings.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            Toast.makeText(this,addresses.get(0).getLocality(), Toast.LENGTH_SHORT).show();
            String city=addresses.get(0).getLocality();
            String wilaya=addresses.get(0).getAdminArea();
            String link="https://api.openweathermap.org/data/2.5/forecast?lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&appid=7b77e2f70d3bf662b1941efa517c5a7a&cnt=100&units=metric";


            String key="7b77e2f70d3bf662b1941efa517c5a7a";
            RequestQueue queue= Volley.newRequestQueue(HomeActivity.this);

            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {


                        progressBar.setVisibility(View.GONE);
                        weatherhome.setVisibility(View.VISIBLE);
                        String iconString =response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
                        String dayOrngiht =response.getJSONArray("list").getJSONObject(0).getJSONObject("sys").getString("pod");

                        citynamev.setText(wilaya+", "+city);

                        String iconlink="https://openweathermap.org/img/wn/"+iconString+"@4x.png";

                        toolbar.setTitle(city);




                        //icon.setImageResource(R.drawable.clearskyday);
                       // Picasso.get().load(iconlink).into(icon);
                        int tempS=response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("temp");
                        tempv.setText(String.valueOf(tempS));
                        String condition=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                        conditionv.setText(condition);
                        int drawbleResult=getWeatherIcon(iconString);

                        icon.setImageResource(drawbleResult);



                        String date=response.getJSONArray("list").getJSONObject(0).getString("dt_txt");
                        String time=date.substring(11,16);




                        int tempp0=response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("temp");
                        String weatherdes=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                        String iconname=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
                        String windspeed=response.getJSONArray("list").getJSONObject(0).getJSONObject("wind").getString("speed");


                        for (int i=0;i<9;i++)
                        {
                            String datei=response.getJSONArray("list").getJSONObject(i).getString("dt_txt");
                            String timei=datei.substring(11,16);
                            int tempp=response.getJSONArray("list").getJSONObject(i).getJSONObject("main").getInt("temp");
                            String humidity=response.getJSONArray("list").getJSONObject(i).getJSONObject("main").getString("humidity");
                            //String weatherdes=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                            String iconnamei=response.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                            String windspeedi=response.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getString("speed");  //meter/sec
                            //Toast.makeText(getApplicationContext(),humidity,Toast.LENGTH_SHORT).show();
                            weatherForcaste.add(new WeatherModel(timei,String.valueOf(tempp)+" °C",iconnamei,windspeedi+"m/s", humidity));
                           // weatherForcastedaily.add(new WeatherModelDaily(timei,String.valueOf(tempp),String.valueOf(tempp),iconnamei,iconnamei,windspeedi+"m/s"));

                        }




                        Date dt = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(dt);
                        c.add(Calendar.DATE, 1); //day +1
                        dt = c.getTime();


                        Date dt2 = new Date();
                        Calendar c2 = Calendar.getInstance();
                        c2.setTime(dt2);
                        c2.add(Calendar.DATE, 2); //day +1
                        dt2 = c2.getTime();




                        Date dt3 = new Date();
                        Calendar c3 = Calendar.getInstance();
                        c3.setTime(dt3);
                        c3.add(Calendar.DATE, 3); //day +1
                        dt3 = c3.getTime();




                        Date dt4 = new Date();
                        Calendar c4 = Calendar.getInstance();
                        c4.setTime(dt4);
                        c4.add(Calendar.DATE, 4); //day +1
                        dt4 = c4.getTime();



                        Date dt5 = new Date();
                        Calendar c5 = Calendar.getInstance();
                        c5.setTime(dt5);
                        c5.add(Calendar.DATE, 5); //day +1
                        dt5 = c5.getTime();

                        String currentDatePlusOne = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dt);
                        String currentDatePlusTwo = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dt2);
                        String currentDatePlusThree = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dt3);
                        String currentDatePlusFour = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dt4);
                        String currentDatePlusFive= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dt5);




                        Long sunset=response.getJSONObject("city").getLong("sunset");
                        Long sunriselong=response.getJSONObject("city").getLong("sunrise");
                        Date longs=new Date(sunset*1000);
                        Date longss=new Date(sunriselong*1000);


                        sunsetV.setText(longs.toString().substring(11,16));
                        sunrise.setText(longss.toString().substring(11,16));

                      // Toast.makeText(HomeActivity.this, longs.toString().substring(11,16), Toast.LENGTH_SHORT).show();



                        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                        String goal = outFormat.format(dt); //get day name

                        //String datnext=response.getJSONArray("list").getJSONObject(4).getString("dt_txt");

                        //
                        Calendar cc=Calendar.getInstance();
                        cc.setTimeInMillis(1657515253);


                        Toast.makeText(HomeActivity.this, currentDatePlusOne, Toast.LENGTH_SHORT).show();

                        for (int i=8;i<100;i=i+2)
                        {
                            String datei=response.getJSONArray("list").getJSONObject(i).getString("dt_txt");
                            String windspeedd=response.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getString("speed");  //meter/sec
                            int tempp=response.getJSONArray("list").getJSONObject(i).getJSONObject("main").getInt("temp");
                            //String weatherdes=response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                            String iconnamei=response.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");

                            String onlyday=datei.substring(0,10);
                            String onlydtime=datei.substring(11);
                            String dayname=null;
                            if (currentDatePlusOne.equals(onlyday))
                            {
                                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(currentDatePlusOne);
                                dayname = outFormat.format(date1); //get day name

                            }
                            else if (currentDatePlusTwo.equals(onlyday))
                            {
                                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(currentDatePlusTwo);
                                dayname = outFormat.format(date1); //get day name
                            }
                            else if (currentDatePlusThree.equals(onlyday))
                            {
                                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(currentDatePlusThree);
                                dayname = outFormat.format(date1); //get day name
                            }
                            else if (currentDatePlusFour.equals(onlyday))
                            {
                                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(currentDatePlusFour);
                                dayname = outFormat.format(date1); //get day name
                            }
                            else if (currentDatePlusFive.equals(onlyday))
                            {
                                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(currentDatePlusFive);
                                dayname = outFormat.format(date1); //get day name
                            }

                            weatherForcastedaily.add(new WeatherModelDaily(dayname+" "+onlydtime,String.valueOf(tempp)+" °C",iconnamei,windspeedd+" m/s"));
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();



                    } catch (ParseException e) {
                        e.printStackTrace();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menutoolbar,menu);
        return true;
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
           // Toast.makeText(this, "failed to get location", Toast.LENGTH_SHORT).show();
            getLocation();
            e.printStackTrace();
        }
    }
}