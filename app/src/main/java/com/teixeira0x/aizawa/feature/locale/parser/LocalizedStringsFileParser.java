package com.teixeira0x.aizawa.feature.l10n.parser;

import com.teixeira0x.aizawa.core.strings.file.StringsFileParser;
import com.teixeira0x.aizawa.utils.FileUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalizedStringsFileParser implements StringsFileParser {
  @Override
  public Map<String, String> parse(InputStream inputStream) {
    Map<String, String> strings = new HashMap<>();
    try {
      String content = FileUtils.readFromInputStream(inputStream);
      List<String> lines = content.lines().toList();

      for (int i = 0; i < lines.size(); i++) {
        String line = lines.get(i).trim();
        if (line.startsWith("#") || line.isEmpty()) {
          // If the line starts with '#', it is a comment and can be skipped. If the line is empty,
          // it can also be skipped.
          continue;
        }

        String[] parts = line.split("=");

        if (parts.length != 2) {
          throw new IllegalStateException(
              "Two arguments are expected on a text line, the key and the value 'key=value'");
        }

        String key = parts[0].trim();
        String value = parts[1];

        strings.put(key, value);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return strings;
  }
}
