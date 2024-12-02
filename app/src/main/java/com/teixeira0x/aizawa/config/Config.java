package com.teixeira0x.aizawa.config;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.teixeira0x.aizawa.utils.FileUtils;

public class Config {
  private static Config sInstance = null;

  public static Config getInstance() {
    try {
      if (sInstance == null)
        sInstance = new Gson().fromJson(FileUtils.readFromResources("config.json"), Config.class);

      return sInstance;
    } catch (Exception e) {
      return null;
    }
  }

  @SerializedName("BOT_TOKEN") private String botToken;

  @SerializedName("BOT_GUILD_ID") private String botGuildId;

  @SerializedName("BOT_LOG_CHANNEL_ID") private String botLogChannelId;

  @SerializedName("MONGO_URI") private String mongoUri;

  public Config(String botToken, String botGuildId, String botLogChannelId, String mongoUri) {
    this.botToken = botToken;
    this.botGuildId = botGuildId;
    this.botLogChannelId = botLogChannelId;
    this.mongoUri = mongoUri;
  }

  public String getBotToken() {
    return this.botToken;
  }

  public void setBotToken(String botToken) {
    this.botToken = botToken;
  }

  public String getBotGuildId() {
    return this.botGuildId;
  }

  public void setBotGuildId(String botGuildId) {
    this.botGuildId = botGuildId;
  }

  public String getBotLogChannelId() {
    return this.botLogChannelId;
  }

  public void setBotLogChannelId(String botLogChannelId) {
    this.botLogChannelId = botLogChannelId;
  }

  public String getMongoUri() {
    return this.mongoUri;
  }

  public void setMongoUri(String mongoUri) {
    this.mongoUri = mongoUri;
  }
}
