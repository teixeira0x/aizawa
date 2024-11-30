package com.teixeira0x.aizawa.core.database.collections;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.teixeira0x.aizawa.database.entity.UserEntity;
import java.util.List;

public interface IUserCollection {
  List<UserEntity> getAll();

  InsertOneResult insertUser(UserEntity userEntity);

  UpdateResult updateUser(long userId, UserEntity newUser);

  UserEntity findUser(long userId);

  DeleteResult removeUser(long userId);
}
