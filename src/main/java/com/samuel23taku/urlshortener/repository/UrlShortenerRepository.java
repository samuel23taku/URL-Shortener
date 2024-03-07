package com.samuel23taku.urlshortener.repository;

import com.samuel23taku.urlshortener.model.UrlModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlShortenerRepository extends MongoRepository<UrlModel,String> {
}
