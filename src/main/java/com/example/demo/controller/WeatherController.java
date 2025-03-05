package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.WeatherData;
import com.example.demo.service.WeatherService;

@RestController

public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
   
    @GetMapping("/v1/weather")
    public WeatherData getWeather(@RequestParam(defaultValue = "Melbourne") String city) {
        return weatherService.getWeatherData(city);
    }
}
