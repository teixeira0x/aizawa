package com.teixeira0x.aizawa.core.strings.file;

import jakarta.annotation.Nonnull;
import java.util.Map;
import java.io.InputStream;

public interface StringsFileParser {

  @Nonnull
  Map<String, String> parse(@Nonnull InputStream inputStream);
}
