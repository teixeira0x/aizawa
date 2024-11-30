package com.teixeira.aizawa.config;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.teixeira.aizawa.utils.FileUtils;

public class Config {

  public static Config getInstance() {
    try {
      return new Gson().fromJson(FileUtils.readFromResources("config.json"), Config.class);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SerializedName("BOT_TOKEN")
  private String botToken;

  @SerializedName("MONGO_URI")
  private String mongoUri;

  public Config(String botToken, String mongoUri) {
    this.botToken = botToken;
    this.mongoUri = mongoUri;
  }

  public String getBotToken() {
    return this.botToken;
  }

  public void setBotToken(String botToken) {
    this.botToken = botToken;
  }

  public String getMongoUri() {
    return this.mongoUri;
  }

  public void setMongoUri(String mongoUri) {
    this.mongoUri = mongoUri;
  }
}
