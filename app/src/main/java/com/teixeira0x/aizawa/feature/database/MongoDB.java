package com.teixeira0x.aizawa.feature.database;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.teixeira0x.aizawa.Aizawa;
import com.teixeira0x.aizawa.core.database.entity.UserEntity;
import com.teixeira0x.aizawa.feature.database.collections.UserCollection;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoDB {
  private static MongoDB sInstance;

  public static MongoDB getInstance() {
    if (sInstance == null)
      sInstance = new MongoDB();
    return sInstance;
  }

  private MongoClient mongoClient;
  private MongoDatabase database;
  private UserCollection userCollection;

  private MongoDB() {
    try {
      mongoClient = MongoClients.create(getSettings());
      database = mongoClient.getDatabase("aizawa");
      userCollection = new UserCollection(database.getCollection("users", UserEntity.class));
    } catch (MongoException e) {
      throw new RuntimeException("An error occurred when trying to connect to MongoDB", e);
    }
  }

  private MongoClientSettings getSettings() {
    ConnectionString connectionString = new ConnectionString(Aizawa.getInstance().getConfig().getMongoUri());

    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    return MongoClientSettings.builder()
        .codecRegistry(pojoCodecRegistry)
        .applyConnectionString(connectionString)
        .build();
  }

  public UserCollection getUserCollection() {
    return this.userCollection;
  }
}
