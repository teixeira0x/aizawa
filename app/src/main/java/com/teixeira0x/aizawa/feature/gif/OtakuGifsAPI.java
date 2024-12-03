package com.teixeira0x.aizawa.feature.gif;

import com.teixeira0x.aizawa.feature.gif.model.GifURL;
import com.teixeira0x.aizawa.utils.RequestUtils;

public class OtakuGifsAPI {
  public static GifURL get(String reaction) {
    try {
      return RequestUtils.get(
          GifURL.class, String.format("https://api.otakugifs.xyz/gif?reaction=%s&format=gif", reaction));
    } catch (Exception err) {
      return null;
    }
  }
}
