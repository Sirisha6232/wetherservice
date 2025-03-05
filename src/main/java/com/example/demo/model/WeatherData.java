package com.example.demo.model;

public class WeatherData {
    private double temperatureDegrees;
    private double windSpeed;

    // No-arg constructor for deserialization
    public WeatherData() {}

    // Constructor with fields
    public WeatherData(double temperatureDegrees, double windSpeed) {
        this.temperatureDegrees = temperatureDegrees;
        this.windSpeed = windSpeed;
    }

    // Getters and Setters
    public double getTemperatureDegrees() {
        return temperatureDegrees;
    }

    public void setTemperatureDegrees(double temperatureDegrees) {
        this.temperatureDegrees = temperatureDegrees;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "temperatureDegrees=" + temperatureDegrees +
                ", windSpeed=" + windSpeed +
                '}';
    }
}
