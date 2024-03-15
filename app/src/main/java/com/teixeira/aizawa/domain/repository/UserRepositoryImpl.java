package com.teixeira.aizawa.domain.repository;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.teixeira.aizawa.database.MongoDB;
import com.teixeira.aizawa.database.entity.UserEntity;
import com.teixeira.aizawa.domain.model.UserModel;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
  private static UserRepository sInstance;

  public static UserRepository getInstance() {
    if (sInstance == null)
      sInstance = new UserRepositoryImpl();
    return sInstance;
  }

  private MongoCollection<UserEntity> collection;

  private UserRepositoryImpl() {
    collection = MongoDB.getInstance().getUserCollection();
  }

  @Override
  public List<UserModel> getAll() {
    List<UserModel> allUsers = new ArrayList<>();
    try {
      collection.find().forEach(userEntity -> { allUsers.add(entityToModel(userEntity)); });
    } catch (MongoException e) {
      e.printStackTrace();
    }
    return allUsers;
  }

  @Override
  public InsertOneResult insertUser(UserModel userModel) {
    InsertOneResult result = InsertOneResult.unacknowledged();
    try {
      result = collection.insertOne(modelToEntity(userModel));
    } catch (MongoException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public UserModel findUser(long userId) {
    try {
      UserModel result = entityToModel(collection.find(Filters.eq("userId", userId)).first());

      return result;
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

  private UserEntity modelToEntity(UserModel user) {
    if (user != null) {
      return new UserEntity(user.id(), user.balance());
    }
    return null;
  }

  private UserModel entityToModel(UserEntity user) {
    if (user != null) {
      return new UserModel(user.getUserId(), user.getBalance());
    }
    return null;
  }
}
