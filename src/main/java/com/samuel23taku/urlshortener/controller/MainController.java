package com.samuel23taku.urlshortener.controller;


import com.samuel23taku.urlshortener.model.UrlModel;
import com.samuel23taku.urlshortener.service.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/link_shortened")
public class MainController {
    final
    ShortenerService shortenerService;

    public MainController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping("/createShortUrl")
    public ResponseEntity createShortUrl(String longUrl){
        if (longUrl == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing parameters");
        }
        return ResponseEntity.status(HttpStatus.OK).body(shortenerService.generateUrlAndReturn(longUrl));
    }
    @GetMapping("/getLongUrl")
//    Get long Url from short url
    public ResponseEntity getLongUrlFromShort(@RequestParam String shortUrl){
        if (shortUrl == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing parameters");
        }
        return ResponseEntity.status(HttpStatus.OK).body(shortenerService.getOriginalUrl(shortUrl));
    }
}
