package com.teixeira0x.aizawa.feature.gif.model;
import com.google.gson.annotations.SerializedName;

public class GifURL {
  @SerializedName("url") private String url;

  public GifURL(String url) {
    this.url = url;
  }

  public String getUrl() {
    return this.url;
  }
}
