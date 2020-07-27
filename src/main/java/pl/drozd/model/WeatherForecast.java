package pl.drozd.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.drozd.config.ConfigAPI;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherForecast {

    ConfigAPI config;

    private String cityName;
    private String lat;
    private String lon;

    JSONObject jsonWithCityData;
    JSONObject jsonWithCurrentWeatherData;
    JSONArray jsonWithDailyWeatherData;

    private StringProperty temperature;
    private StringProperty description;
    private StringProperty date;
    private boolean jsonDataCorrect = true;

    public WeatherForecast(String cityName){
        this.cityName = cityName;
        config = new ConfigAPI();
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setLat(){
        this.lat = jsonWithCityData.getJSONObject("coord").get("lat").toString();
    }

    public void setLon(){
        this.lon = jsonWithCityData.getJSONObject("coord").get("lon").toString();
    }

    public void getWeather(int daysFromToday) throws IOException {
        jsonWithCityData = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?q="+this.cityName+"&appid="+config.getApiKey()+"&lang=pl&units=metric");
        if(isDataCorrect()){
            jsonDataCorrect = true;
            jsonWithCurrentWeatherData = jsonWithCityData.getJSONObject("main");

            setLat();
            setLon();

            JSONObject jsonWithWeatherData = readJsonFromUrl("https://api.openweathermap.org/data/2.5/onecall?lat="+this.lat+"&lon="+this.lon+"& exclude=daily&appid="+config.getApiKey()+"&lang=pl&units=metric");
            jsonWithDailyWeatherData = jsonWithWeatherData.getJSONArray("daily");
            setWeather(daysFromToday);
        }
        else{
            jsonDataCorrect = false;
        }
    }

    private boolean isDataCorrect() {
        if(jsonWithCityData.getInt("cod") == 404){
            return false;
        }
        return true;
    }

    public boolean getJsonDataCorrect(){
        return  this.jsonDataCorrect;
    }


    public JSONObject readJsonFromUrl(String url) throws JSONException {

        String jsonText;
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            jsonText = readAll(bufferedReader);
            JSONObject json = new JSONObject(jsonText);
            is.close();
            return json;
        } catch (IOException e){
            jsonText = "{\"cod\":\"404\",\"message\":\"city not found\"}";
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }

    private String readAll(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            stringBuilder.append((char) cp);
        }
        return stringBuilder.toString();
    }

    public void setDescription(int daysFromToday){
        this.description = new SimpleStringProperty(jsonWithDailyWeatherData.getJSONObject(daysFromToday).getJSONArray("weather").getJSONObject(0).getString("description"));
    }

    public StringProperty getDescription(){
        return this.description;
    }

    public void setDate(int daysFromToday){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, daysFromToday);

        date = calendar.getTime();
        this.date = new SimpleStringProperty(simpleDateFormat.format(date));
    }

    public StringProperty getDate(){
        return this.date;
    }

    private void setWeather(int daysFromToday){
        setTemperature(daysFromToday);
        setDescription(daysFromToday);
        setDate(daysFromToday);
    }

    public void setTemperature(int daysFromToday) {
        String temp = String.valueOf(jsonWithDailyWeatherData.getJSONObject(daysFromToday).getJSONObject("temp").getInt("max")) +"\u2103";
        this.temperature = new SimpleStringProperty (temp);
    }

    public StringProperty getTemperatureForSpecificDay(){
        return this.temperature;
    }
}
