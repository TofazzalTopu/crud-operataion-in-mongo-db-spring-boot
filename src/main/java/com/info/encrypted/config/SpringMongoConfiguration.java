package com.info.encrypted.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@Configuration
//@EnableMongoRepositories("com.info.encrypted.repository")
public class SpringMongoConfiguration extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoDB;

    @Value("${spring.data.mongodb.database}")
    private String mongoURI;

    @Override
    protected String getDatabaseName() {
        // TODO Auto-generated method stub
        return mongoDB;
    }

//    @Override
    /*public MongoMappingContext mongoMappingContext()
            throws ClassNotFoundException {
        // TODO Auto-generated method stub
        return super.mongoMappingContext();
    }*/

//    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(mongoURI));
    }
}
