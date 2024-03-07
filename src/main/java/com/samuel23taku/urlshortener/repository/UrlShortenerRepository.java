package com.samuel23taku.urlshortener.repository;

import com.samuel23taku.urlshortener.model.UrlModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UrlShortenerRepository extends MongoRepository<UrlModel,String> {
    @Query("{originalUrl: '?0'}")
    UrlModel findExistingOriginalUrl(String originalUrl);
}
