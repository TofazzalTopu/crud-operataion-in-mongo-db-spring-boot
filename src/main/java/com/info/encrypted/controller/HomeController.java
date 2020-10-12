package com.info.encrypted.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HomeController {

    @RequestMapping(value = "/success")
    public String successMsg() {

        System.out.println("Encrypted datasource successfully read from properties file using Jasypt encryptor.");
        return "Encrypted datasource successfully read from properties file using Jasypt encryptor.";
    }



}
