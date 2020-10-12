package com.info.encrypted.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import org.bson.Document;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private Environment env;

    //    @Bean
    /*public DataSource getDataSource() {
        Map<String, String> map = getPass();
        String client_url = "mongodb://" + map.get("userName") + ":" + map.get("decryptedPassword") + "@" + map.get("host") + ":" + map.get("port") + "/" + map.get("database");

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("om.mongodb.Mongo");
//        dataSourceBuilder.driverClassName("oracle.jdbc.OracleDriver");
        dataSourceBuilder.url(client_url);
//        dataSourceBuilder.url(map.get("host"));
        dataSourceBuilder.username(map.get("userName"));
        dataSourceBuilder.password(map.get("decryptedPassword"));
        return dataSourceBuilder.build();
    }*/

    @Bean
    public MongoClient getDataSource() {
        Map<String, String> map = getPass();

        /*MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("journaldev");
        boolean auth = db.authenticate("pankaj", "pankaj123".toCharArray());
*/

        // Mongodb initialization parameters.
        int port_no = 27017;
        String auth_user = map.get("userName"), auth_pwd = map.get("decryptedPassword"), host_name = map.get("host"), db_name = "mongoauthdemo", db_col_name = "tutorials", encoded_pwd = "";

        // Mongodb connection string.
        String client_url = "mongodb://" + map.get("userName") + ":" + map.get("decryptedPassword") + "@" + map.get("host") + ":" + map.get("port") + "/" + map.get("database");
        MongoClientURI uri = new MongoClientURI(client_url);

        // Connecting to the mongodb server using the given client uri.
//        MongoClient mongo_client = new MongoClient(uri);
        MongoClient mongoClient1 = MongoClients.create();
        MongoClient mongoClient = MongoClients.create("mongodb://27017");
        // Fetching the database from the mongodb.
        MongoDatabase db = mongoClient1.getDatabase(map.get("database"));
//        MongoDatabase db = mongo_client.getDatabase(db_name);

        // Fetching the collection from the mongodb.
        MongoCollection<Document> coll = db.getCollection(db_col_name);
        logger.info("Fetching all documents from the collection");

        // Performing a read operation on the collection.
        FindIterable<Document> fi = coll.find();
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while (cursor.hasNext()) {
                // logger.info(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        return mongoClient;
    }

    public void grt() {
        List<ServerAddress> seeds = new ArrayList<ServerAddress>();
        seeds.add(new ServerAddress("localhost"));
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        char[] pass = "pass".toCharArray();

        credentials.add(MongoCredential.createScramSha1Credential(
                "user",
                "db",
                pass
        ));

        /*MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(seeds))
                        .credential(credentials)
                        .build());
        */
        /*boolean add = credentials.add(
                MongoCredential.createMongoCRCredential(
                        "app_user",
                        "data",
                        "bestPo55word3v3r".toCharArray()
                )
        );
        MongoClient mongo = MongoClients.create( seeds, credentials );*/
    }


    @Bean
    public Mongo mongoo() throws Exception {
        Map<String, String> map = getPass();
        String client_url = "mongodb://" + map.get("userName") + ":" + map.get("decryptedPassword") + "@" + map.get("host") + ":" + map.get("port") + "/" + map.get("database");
        MongoClientURI uri = new MongoClientURI(client_url);
        System.out.println("client_url " + client_url);
        return new com.mongodb.MongoClient(new MongoClientURI(client_url));
    }

    public Map<String, String> getPass() {

        String mpCryptoPassword = "MY_SECRET";

        Map<String, String> map = new HashMap<>();
        String port = env.getProperty("spring.data.mongodb.port");
        String host = env.getProperty("spring.data.mongodb.host");
        String encryptedUserName = env.getProperty("spring.datasource.username");
        String encryptedPassword = env.getProperty("spring.datasource.password");
        String database = env.getProperty("spring.data.mongodb.database");

        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(mpCryptoPassword);
        String decryptedUsername = decryptor.decrypt(encryptedUserName);
        String decryptedPassword = decryptor.decrypt(encryptedPassword);
        System.out.println(decryptor.decrypt(encryptedPassword));

        map.put("port", port);
        map.put("host", host);
        map.put("userName", decryptedUsername);
        map.put("decryptedPassword", decryptedPassword);
        map.put("database", decryptor.decrypt(database));
        return map;
    }
}
