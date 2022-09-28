package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Settings extends AppCompatActivity {

    LinearLayout locationtype,units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initWidget();


        locationtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseunitDialog chooseunitDialog=new chooseunitDialog();
                chooseunitDialog.show(getSupportFragmentManager(),"dialog");
            }
        });
    }

    private void initWidget() {
        locationtype=findViewById(R.id.locationtype);
        units=findViewById(R.id.units);
    }
}