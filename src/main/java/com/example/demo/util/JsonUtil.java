package com.example.demo.util;

import com.example.demo.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static WeatherData parseWeatherStackResponse(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            double temperature = node.get("current").get("temperature").asDouble();
            double windSpeed = node.get("current").get("wind_speed").asDouble();
            return new WeatherData(temperature, windSpeed);
        } catch (Exception e) {
            System.out.println("Failed to parse WeatherStack response: " + e.getMessage());
        }
        return null;
    }

    public static WeatherData parseOpenWeatherMapResponse(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            //sample node:
            //{"coord":{"lon":144.9633,"lat":-37.814},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"base":"stations","main":{"temp":294.71,"feels_like":294.8,"temp_min":294.71,"temp_max":294.71,"pressure":1020,"humidity":72,"sea_level":1020,"grnd_level":1016},"visibility":10000,"wind":{"speed":5.08,"deg":204,"gust":6.45},"clouds":{"all":0},"dt":1741166147,"sys":{"country":"AU","sunrise":1741118909,"sunset":1741164916},"timezone":39600,"id":2158177,"name":"Melbourne","cod":200}
            double temperature = node.get("main").get("temp").asDouble() - 273.15; // Convert Kelvin to Celsius
            double windSpeed = node.get("wind").get("speed").asDouble();
            return new WeatherData(temperature, windSpeed);
        } catch (Exception e) {
            System.out.println("Failed to parse OpenWeatherMap response: " + e.getMessage());
        }
        return null;
    }
}

