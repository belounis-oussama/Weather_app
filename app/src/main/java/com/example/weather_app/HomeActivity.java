package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {


    TextView citynamev,tempv,conditionv;
    EditText citynametext;
    ImageView backgroud,icon,searchicon;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layouyt;
    List<WeatherModel> weatherForcaste;

    private WeatherAdapter adapter;
    private ArrayList<WeatherModel> weatherModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        initWidget();


        String link="https://api.openweathermap.org/data/2.5/forecast?q=gdyel&appid=7b77e2f70d3bf662b1941efa517c5a7a&cnt=3&units=metric";


        String key="7b77e2f70d3bf662b1941efa517c5a7a";
        RequestQueue queue= Volley.newRequestQueue(HomeActivity.this);
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

                    Toast.makeText(HomeActivity.this,response.getJSONArray("list").getJSONObject(0).getJSONObject("wind").getString("speed"), Toast.LENGTH_SHORT).show();

                    String iconlink="https://openweathermap.org/img/wn/"+iconString+"@4x.png";




                    String n="";
                    Picasso.get().load(iconlink).into(icon);
                    int tempS=response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("temp");
                    tempv.setText(String.valueOf(tempS));
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
                Toast.makeText(HomeActivity.this,"error"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });


        queue.add(request);


        weatherForcaste = new ArrayList<>();

        weatherForcaste.add(new WeatherModel("hh","skks","skks","sffa"));



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
    }
}