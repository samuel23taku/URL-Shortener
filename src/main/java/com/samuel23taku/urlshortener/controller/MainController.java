package com.samuel23taku.urlshortener.controller;


import com.samuel23taku.urlshortener.model.UrlModel;
import com.samuel23taku.urlshortener.service.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/link_shortener")
public class MainController {
    final
    ShortenerService shortenerService;

    public MainController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping("/createShortUrl")
    public ResponseEntity createShortUrl(@RequestBody String longUrl){
        System.out.println("Long url is "+longUrl);
        if (longUrl == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing parameters");
        }
        return ResponseEntity.status(HttpStatus.OK).body(shortenerService.generateUrlAndReturn(longUrl));
    }
    @GetMapping("/getLongUrl")
//    Get long Url from short url
    public ResponseEntity getLongUrlFromShort(@RequestParam String shortUrl){
        System.out.println("Url is "+shortUrl);
        if (shortUrl == null) {
//            Todo (If value doesn't exist in Db tell a user about creating a new one)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing parameters");
        }
        return ResponseEntity.status(HttpStatus.OK).body(shortenerService.getOriginalUrl(shortUrl));
    }
}
