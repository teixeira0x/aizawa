package com.teixeira.aizawa.database;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.teixeira.aizawa.Main;
import com.teixeira.aizawa.database.entity.UserEntity;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoDB {

  private static MongoDB sInstance;

  public static MongoDB getInstance() {
    if (sInstance == null) sInstance = new MongoDB();
    return sInstance;
  }

  private MongoClient mongoClient;
  private MongoDatabase database;

  private MongoDB() {
    try {
      mongoClient = MongoClients.create(getSettings());
      database = mongoClient.getDatabase("simulator");
    } catch (MongoException e) {
      throw new RuntimeException("An error occurred when trying to connect to MongoDB!", e);
    }
  }

  private MongoClientSettings getSettings() {
    ConnectionString connectionString = new ConnectionString(Main.dotenv().get("MONGO_URI"));

    CodecRegistry pojoCodecRegistry =
        fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    return MongoClientSettings.builder()
        .codecRegistry(pojoCodecRegistry)
        .applyConnectionString(connectionString)
        .build();
  }

  public MongoCollection<UserEntity> getUserCollection() {
    return database.getCollection("users", UserEntity.class);
  }
}
