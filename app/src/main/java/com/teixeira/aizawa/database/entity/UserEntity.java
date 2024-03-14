package com.teixeira.aizawa.database.entity;

import java.math.BigDecimal;
import org.bson.types.ObjectId;

public class UserEntity {

  private ObjectId id;
  private long userId;
  private BigDecimal balance;

  public UserEntity(long userId, BigDecimal balance) {
    this.userId = userId;
    this.balance = balance;
  }

  public UserEntity() {}

  public ObjectId getId() {
    return this.id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public long getUserId() {
    return this.userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public BigDecimal getBalance() {
    return this.balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
}
