package com.example.weather_app;

public class WeatherModel {

    private String time;
    private String tempreture;
    private String icon;
    private String windSpeed;
    private String humidity;

    public WeatherModel() {
    }

    public WeatherModel(String time, String tempreture, String icon, String windSpeed, String humidityy) {
        this.time = time;
        this.tempreture = tempreture;
        this.icon = icon;
        this.windSpeed = windSpeed;
        this.humidity = humidityy;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTempreture() {
        return tempreture;
    }

    public void setTempreture(String tempreture) {
        this.tempreture = tempreture;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "time='" + time + '\'' +
                ", tempreture='" + tempreture + '\'' +
                ", icon='" + icon + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
