package com.samuel23taku.urlshortener.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel23taku.urlshortener.model.UrlModel;
import com.samuel23taku.urlshortener.repository.UrlShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class ShortenerService {
    final
    UrlShortenerRepository urlShortenerRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public ShortenerService(RedisTemplate<String, String> redisTemplate, UrlShortenerRepository urlShortenerRepository, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.urlShortenerRepository = urlShortenerRepository;
        this.objectMapper = objectMapper;
    }

    public UrlModel generateUrlAndReturn(String originalUrl){


    }

//    If url doesn't exist in db create one
    public UrlModel getOriginalUrl(String url){

    }

    private UrlModel getLongUrlFromRedis(String shortenedUrl) {
        try {
            String json = redisTemplate.opsForValue().get(shortenedUrl);
            System.out.println("Shortened Url is "+json);
            return new UrlModel("id","oringla","shorted","created data");
        } catch (Exception e) {
            return null;
        }
    }

    private void cacheUrlInRedis(UrlModel model){
        try {
            long ttlInHours = 1;
            String mappedUrlModel = objectMapper.writeValueAsString(model);
            redisTemplate.opsForValue().set(model.id(), mappedUrlModel, ttlInHours, TimeUnit.HOURS);
        } catch (Exception e) {

            System.out.println("Error saving");
            //Todo(Log here)
        }
    }

}
