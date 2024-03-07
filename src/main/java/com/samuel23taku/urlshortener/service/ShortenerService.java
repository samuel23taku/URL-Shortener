package com.samuel23taku.urlshortener.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel23taku.urlshortener.model.UrlModel;
import com.samuel23taku.urlshortener.repository.UrlShortenerRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


@Component
public class ShortenerService {
    final UrlShortenerRepository urlShortenerRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public ShortenerService(RedisTemplate<String, String> redisTemplate, UrlShortenerRepository urlShortenerRepository, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.urlShortenerRepository = urlShortenerRepository;
        this.objectMapper = objectMapper;
    }

    public UrlModel generateUrlAndReturn(String originalUrl) {
        UrlModel existingUrl = urlShortenerRepository.findExistingOriginalUrl(originalUrl);
        if (existingUrl != null) {
//            If it exists in the DB return it
            return existingUrl;
        }
//            If it doesn't exist in the DB create a new one
        UrlModel generatedUrlModel = utilGenerateUrl(originalUrl);
        urlShortenerRepository.insert(generatedUrlModel);
        return generatedUrlModel;
    }

    //    If url doesn't exist in db create one
    public UrlModel getOriginalUrl(String shortUrl) {
        UrlModel urlModel = getLongUrlFromRedis(shortUrl);
        if (urlModel != null) {
            return urlModel;
        }

        var value = urlShortenerRepository.findById(shortUrl);
        System.out.println("value is "+value);
////        Cache the result in redis for future use
        cacheUrlInRedis(value.get());
        return value.get();
    }

    private UrlModel getLongUrlFromRedis(String shortenedUrl) {
        try {
            String json = redisTemplate.opsForValue().get(shortenedUrl);
            UrlModel res = objectMapper.readValue(json, new TypeReference<UrlModel>() {
            });
            System.out.println("Shortened Url is " + json);
            return res;
//            return new UrlModel("id", "oringla", "shorted", "created data");
        } catch (Exception e) {
            return null;
        }
    }

    private void cacheUrlInRedis(UrlModel model) {
        try {
            long ttlInHours = 1;
            String mappedUrlModel = objectMapper.writeValueAsString(model);
            redisTemplate.opsForValue().set(model.id(), mappedUrlModel, ttlInHours, TimeUnit.HOURS);
        } catch (Exception e) {

            System.out.println("Error saving");
            //Todo(Log here)
        }
    }

    private UrlModel utilGenerateUrl(String url) {
        LocalDate currentDate = LocalDate.now(); // Get the current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Specify the desired format

        String formattedDate = currentDate.format(formatter);
        String newLink = "new_link"+Math.random();
//        I'm using the new link as an id
        return new UrlModel(newLink, url,  newLink, formattedDate);
    }
}
