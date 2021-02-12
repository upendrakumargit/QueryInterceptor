package com.apple.gbi.gsf.multiplex.utils;

import java.util.Properties;

public final class MultiplexUtils {

  private MultiplexUtils() {}

  public static boolean isBlank(final CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (Character.isWhitespace(cs.charAt(i)) == false) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean isNotBlank(final CharSequence cs) {
    return !isBlank(cs);
  }
  
  public static String getValue(final Properties properties, final String key) {
    if(null != properties) {
      return properties.getProperty(key);
    }
    return null;
  }

  public static String getQdbHintSql(final String sql, final String qdbHint) {
    final String sqlWithQdbHint =
        isBlank(qdbHint) ? sql : new StringBuilder(qdbHint).append(sql).toString();
    return sqlWithQdbHint;
  }
}
