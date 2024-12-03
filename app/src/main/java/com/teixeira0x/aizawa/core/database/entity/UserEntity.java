package com.teixeira0x.aizawa.core.database.entity;

import java.math.BigDecimal;
import org.bson.types.ObjectId;

public class UserEntity {
  private ObjectId id;
  private long userId;
  private BigDecimal balance;
  private BigDecimal bankBalance;

  public UserEntity(long userId, BigDecimal balance, BigDecimal bankBalance) {
    this.userId = userId;
    this.balance = balance;
    this.bankBalance = bankBalance;
  }

  public UserEntity() {
    balance = BigDecimal.valueOf(0);
    bankBalance = BigDecimal.valueOf(0);
  }

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

  public BigDecimal getBankBalance() {
    return this.bankBalance;
  }

  public void setBankBalance(BigDecimal bankBalance) {
    this.bankBalance = bankBalance;
  }
}
