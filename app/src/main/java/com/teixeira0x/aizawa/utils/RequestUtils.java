package com.teixeira0x.aizawa.utils;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Some useful methods for working with REST API.
 *
 * @author Felipe Teixeira.
 */
public class RequestUtils {
  /**
   * Returns data from the apiURL endpoint.
   *
   * @param type The type of the return class.
   * @param apiURL The endpoint
   */
  public static <T> T get(Class<T> type, String apiURL) throws IOException {
    return get(type, apiURL, null);
  }

  public static <T> T get(Class<T> type, String apiURL, Map<String, String> properties) throws IOException {
    return new Gson().fromJson(get(apiURL, properties), type);
  }

  public static String get(String apiURL) throws IOException {
    return get(apiURL, null);
  }

  public static String get(String apiURL, Map<String, String> properties) throws IOException {
    return request("GET", apiURL, properties);
  }

  public static String request(String method, String apiURL, Map<String, String> properties) throws IOException {
    URL url = new URL(apiURL);

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod(method);

    if (properties != null) {
      for (Map.Entry<String, String> property : properties.entrySet()) {
        conn.setRequestProperty(property.getKey(), property.getValue());
      }
    }

    int responseCode = conn.getResponseCode();

    if (responseCode != 200) {
      throw new RuntimeException("HTTP Error code: " + responseCode);
    }

    InputStreamReader in = new InputStreamReader(conn.getInputStream());
    BufferedReader br = new BufferedReader(in);

    StringBuilder output = new StringBuilder();

    char[] buff = new char[1024];
    int length = 0;

    while ((length = br.read(buff)) > 0) {
      output.append(new String(buff, 0, length));
    }

    conn.disconnect();

    return output.toString(); // Retun the response
  }

  private RequestUtils() {}
}
