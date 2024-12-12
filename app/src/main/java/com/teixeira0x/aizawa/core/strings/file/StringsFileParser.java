package com.teixeira0x.aizawa.core.strings.file;

import jakarta.annotation.Nonnull;
import java.io.InputStream;
import java.util.Map;

public interface StringsFileParser {
  @Nonnull Map<String, String> parse(@Nonnull InputStream inputStream);
}
