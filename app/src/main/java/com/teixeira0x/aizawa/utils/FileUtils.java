package com.teixeira0x.aizawa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
  public static String readFromResources(String name) throws IOException {
    return readFromInputStream(FileUtils.class.getClassLoader().getResourceAsStream(name));
  }

  public static String readFromInputStream(InputStream is) throws IOException {
    InputStreamReader in = new InputStreamReader(is);
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
