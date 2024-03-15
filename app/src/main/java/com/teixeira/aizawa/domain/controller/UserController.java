package com.teixeira.aizawa.domain.controller;

import com.teixeira.aizawa.domain.model.UserModel;
import com.teixeira.aizawa.domain.repository.UserRepository;
import java.math.BigDecimal;
import net.dv8tion.jda.api.entities.User;

public class UserController {
  public static UserModel findOrInsertUser(User discordUser) {
    long userId = discordUser.getIdLong();

    UserRepository repository = UserRepository.getInstance();
    UserModel userModel = repository.findUser(userId);
    if (userModel == null) {
      userModel = new UserModel(userId, BigDecimal.valueOf(0), BigDecimal.valueOf(0));
      repository.insertUser(userModel);
    }

    return userModel;
  }
}
