package com.teixeira.aizawa.domain.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.teixeira.aizawa.domain.model.UserModel;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

  public static UserRepository getInstance() {
    return UserRepositoryImpl.getInstance();
  }

  List<UserModel> getAll();

  Optional<UserModel> getUser(int userId);

  InsertOneResult insertUser(UserModel userModel);

  DeleteResult removeUser(int userId);
}
