package com.samuel23taku.urlshortener.controller;


import com.samuel23taku.urlshortener.model.UrlModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link_shortened")
public class MainController {
    @PostMapping("/createShortUrl")
    public ResponseEntity createShortUrl(String longUrl){
        if (longUrl == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing parameters");
        }
    }

}
