package com.info.encrypted;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableEncryptableProperties
@SpringBootApplication
public class EncryptedApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptedApplication.class, args);
    }

}
