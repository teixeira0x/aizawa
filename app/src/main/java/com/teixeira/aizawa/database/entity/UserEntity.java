package com.teixeira.aizawa.database.entity;

import org.bson.types.ObjectId;

public class UserEntity {

  private ObjectId id;
  private int userId;

  public UserEntity(int userId) {
    this.userId = userId;
  }

  public UserEntity() {
    userId = 0;
  }

  public ObjectId getId() {
    return this.id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public int getUserId() {
    return this.userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
