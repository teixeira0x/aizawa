package com.teixeira.aizawa.controllers;

import com.teixeira.aizawa.database.MongoDB;
import com.teixeira.aizawa.database.collections.UserCollection;
import com.teixeira.aizawa.database.entity.UserEntity;
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
}
