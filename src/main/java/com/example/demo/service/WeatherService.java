package com.example.demo.service;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.model.WeatherData;
import com.example.demo.util.JsonUtil;

import java.io.IOException;

@Service
public class WeatherService {

    private static final String WEATHERSTACK_API = "http://api.weatherstack.com/current";
    private static final String OPENWEATHERMAP_API = "http://api.openweathermap.org/data/2.5/weather";
    private static final String WEATHERSTACK_ACCESS_KEY = "YOUR_ACCESS_KEY";
    private static final String OPENWEATHERMAP_APPID = "2326504fb9b100bee21400190e4dbe6d";
    private static final String CITY = "Melbourne";

    private final OkHttpClient client;

    public WeatherService() {
        this.client = new OkHttpClient();
    }

    @Cacheable(value = "weather", key = "#city", unless = "#result == null")
    public WeatherData getWeatherData(String city) {
        WeatherData data = fetchFromWeatherStack(city);
        if (data == null) {
            data = fetchFromOpenWeatherMap(city);
        }
        return data;
    }

    private WeatherData fetchFromWeatherStack(String city) {
        try {
            Request request = new Request.Builder()
                    .url(WEATHERSTACK_API + "?access_key=" + WEATHERSTACK_ACCESS_KEY + "&query=" + city)
                    .get()
                    .build();
            //Request{method=GET, url=http://api.weatherstack.com/current?access_key=YOUR_ACCESS_KEY&query=Melbourne}
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return JsonUtil.parseWeatherStackResponse(responseBody);
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch from WeatherStack: " + e.getMessage());
        }
        return null;
    }

    private WeatherData fetchFromOpenWeatherMap(String city) {
        try {
            Request request = new Request.Builder()
                    .url(OPENWEATHERMAP_API + "?q=" + city + ",AU&appid=" + OPENWEATHERMAP_APPID)
                    .get()
                    .build();
            //Request{method=GET, url=http://api.openweathermap.org/data/2.5/weather?q=Melbourne,AU&appid=2326504fb9b100bee21400190e4dbe6d}
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                //{"coord":{"lon":144.9633,"lat":-37.814},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"base":"stations","main":{"temp":294.71,"feels_like":294.8,"temp_min":294.71,"temp_max":294.71,"pressure":1020,"humidity":72,"sea_level":1020,"grnd_level":1016},"visibility":10000,"wind":{"speed":5.08,"deg":204,"gust":6.45},"clouds":{"all":0},"dt":1741166147,"sys":{"country":"AU","sunrise":1741118909,"sunset":1741164916},"timezone":39600,"id":2158177,"name":"Melbourne","cod":200}
                return JsonUtil.parseOpenWeatherMapResponse(responseBody);
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch from OpenWeatherMap: " + e.getMessage());
        }
        return null;
    }
}
