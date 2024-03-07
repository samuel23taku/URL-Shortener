package com.samuel23taku.urlshortener.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("shortened_url")
// Will use the id in referencing the new Url by
// Will check if the document has passed TTL  time using its createdDate in a scheduler
public record UrlModel(String id,String originalUrl,String newUrl,String createdDate){

}
