package com.info.encrypted.config;


import org.springframework.dao.DataAccessException;

import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.MongoDatabaseFactory;

/**
 * Interface for factories creating {@link MongoDatabase} instances.
 *
 * @author Mark Pollack
 * @author Thomas Darimont
 * @author Christoph Strobl
 * @deprecated since 3.0, use {@link org.springframework.data.mongodb.MongoDatabaseFactory} instead.
 */
@Deprecated
public interface MongoDbFactory extends MongoDatabaseFactory {

    /**
     * Creates a default {@link MongoDatabase} instance.
     *
     * @return never {@literal null}.
     * @throws DataAccessException
     * @deprecated since 3.0. Use {@link #getMongoDatabase()} instead.
     */
    @Deprecated
    default MongoDatabase getDb() throws DataAccessException {
        return getMongoDatabase();
    }

    /**
     * Obtain a {@link MongoDatabase} instance to access the database with the given name.
     *
     * @param dbName must not be {@literal null} or empty.
     * @return never {@literal null}.
     * @throws DataAccessException
     * @deprecated since 3.0. Use {@link #getMongoDatabase(String)} instead.
     */
    @Deprecated
    default MongoDatabase getDb(String dbName) throws DataAccessException {
        return getMongoDatabase(dbName);
    }
}
