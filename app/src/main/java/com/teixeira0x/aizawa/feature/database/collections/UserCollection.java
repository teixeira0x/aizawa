package com.teixeira0x.aizawa.feature.database.collections;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.teixeira0x.aizawa.core.database.collections.IUserCollection;
import com.teixeira0x.aizawa.core.database.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class UserCollection implements IUserCollection {
  private final MongoCollection<UserEntity> collection;

  public UserCollection(MongoCollection<UserEntity> collection) {
    this.collection = collection;
  }

  @Override
  public List<UserEntity> getAll() {
    List<UserEntity> allUsers = new ArrayList<>();
    try {
      collection.find().forEach(userEntity -> allUsers.add(userEntity));
    } catch (MongoException e) {
      e.printStackTrace();
    }
    return allUsers;
  }

  @Override
  public InsertOneResult insertUser(UserEntity userEntity) {
    InsertOneResult result = InsertOneResult.unacknowledged();
    try {
      result = collection.insertOne(userEntity);
    } catch (MongoException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public UpdateResult updateUser(long userId, UserEntity newUser) {
    UpdateResult result = UpdateResult.unacknowledged();
    try {
      result = collection.updateMany(Filters.eq("userId", userId), new Document("$set", newUser));
    } catch (MongoException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public UserEntity findUser(long userId) {
    try {
      return collection.find(Filters.eq("userId", userId)).first();
    } catch (MongoException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public DeleteResult removeUser(long userId) {
    DeleteResult result = DeleteResult.unacknowledged();
    try {
      result = collection.deleteMany(Filters.in("userId", userId));
    } catch (MongoException e) {
      e.printStackTrace();
    }
    return result;
  }
}
