package com.teixeira0x.aizawa.controllers;

import com.teixeira0x.aizawa.core.database.entity.UserEntity;
import com.teixeira0x.aizawa.feature.database.MongoDB;
import com.teixeira0x.aizawa.feature.database.collections.UserCollection;
import java.math.BigDecimal;
import net.dv8tion.jda.api.entities.User;

public class UserController {
  public static UserEntity findOrInsertUser(User discordUser) {
    long userId = discordUser.getIdLong();

    MongoDB db = MongoDB.getInstance();
    UserCollection userCollection = db.getUserCollection();

    UserEntity userEntity = userCollection.findUser(userId);
    if (userEntity == null) {
      userEntity = new UserEntity(userId, BigDecimal.valueOf(0), BigDecimal.valueOf(0));
      userCollection.insertUser(userEntity);
    }

    return userEntity;
  }

  public static void updateUser(UserEntity userEntity) {
    MongoDB.getInstance().getUserCollection().updateUser(userEntity.getUserId(), userEntity);
  }
}
