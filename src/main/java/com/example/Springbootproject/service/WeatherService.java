package com.example.Springbootproject.service;

import com.example.Springbootproject.api.response.WeatherResponse;
import com.example.Springbootproject.cache.AppCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private  String apiKey; // static variable is related

    @Autowired
    private AppCache appCache;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
       WeatherResponse weatherResponse = redisService.get("weather_of_"+ city, WeatherResponse.class); // when we want users use user.class, pojo class we are giving...
       if(weatherResponse != null) return weatherResponse;
       else {
           String finalApi = appCache.APP_CACHE.get("weather_api").replace("<apiKey>", apiKey).replace("<city>", city);
           ResponseEntity<WeatherResponse> response =
                   restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
           WeatherResponse body = response.getBody();
           if(body!=null) redisService.set("weather_of_"+ city,body,300l);
           return body;
       }
    }
}
