package com.teixeira.aizawa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class FileUtils {

  public static String readFromResources(String name) throws IOException {
    InputStreamReader in =
        new InputStreamReader(FileUtils.class.getClassLoader().getResourceAsStream(name));
    BufferedReader br = new BufferedReader(in);

    StringBuilder output = new StringBuilder();

    char[] buff = new char[1024];
    int length = 0;

    while ((length = br.read(buff)) > 0) {
      output.append(new String(buff, 0, length));
    }

    return output.toString();
  }
}
