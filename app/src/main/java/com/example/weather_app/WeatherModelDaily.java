package com.example.weather_app;

public class WeatherModelDaily {

    private String day;
    private String tempretureday;
    private String iconday;
    private String windSpeed;

    public WeatherModelDaily() {
    }

    public WeatherModelDaily(String day, String tempretureday, String iconday, String windSpeed) {
        this.day = day;
        this.tempretureday = tempretureday;
        this.iconday = iconday;
        this.windSpeed = windSpeed;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTempretureday() {
        return tempretureday;
    }

    public void setTempretureday(String tempretureday) {
        this.tempretureday = tempretureday;
    }

    public String getIconday() {
        return iconday;
    }

    public void setIconday(String iconday) {
        this.iconday = iconday;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "day='" + day + '\'' +
                ", tempretureday='" + tempretureday + '\'' +
                ", icon='" + iconday + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                '}';
    }

}
