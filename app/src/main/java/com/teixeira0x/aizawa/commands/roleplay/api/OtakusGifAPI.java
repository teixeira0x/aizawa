package com.teixeira0x.aizawa.commands.roleplay.api;

import com.teixeira0x.aizawa.utils.RequestUtils;
import java.io.IOException;

public class OtakusGifAPI {
  public static OtakusGifAPI get(String reaction) {
    try {
      return RequestUtils.get(
          OtakusGifAPI.class, String.format("https://api.otakugifs.xyz/gif?reaction=%s&format=gif", reaction));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String url;

  public OtakusGifAPI(String url) {
    this.url = url;
  }

  public String getUrl() {
    return this.url;
  }
}
