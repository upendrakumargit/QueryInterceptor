package com.apple.gbi.gsf.multiplex.constant;

import java.sql.ResultSet;

/**
 * Enum to hold the result set type for result set.
 * 
 * @author UpendraKumar
 */
public enum ResultSetType {
  
  HOLD_CURSORS_OVER_COMMIT(ResultSet.HOLD_CURSORS_OVER_COMMIT),
  CLOSE_CURSORS_AT_COMMIT(ResultSet. CLOSE_CURSORS_AT_COMMIT),
  CONCUR_READ_ONLY(ResultSet.CONCUR_READ_ONLY),
  CONCUR_UPDATABLE(ResultSet.CONCUR_UPDATABLE),
  FETCH_FORWARD(ResultSet.FETCH_FORWARD),
  FETCH_REVERSE(ResultSet.FETCH_REVERSE),
  FETCH_UNKNOWN(ResultSet.FETCH_UNKNOWN),
  TYPE_FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY),
  TYPE_SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE),
  TYPE_SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE),
  NONE(-1);
  
  private int code;
  
  ResultSetType(final int code) {
    this.code = code;
  }
  
  public static int valueOfType(final String type) {
    if(null != type && type != "") {
      for (final ResultSetType resultSetType : ResultSetType.values()) {
        if(resultSetType.name().equalsIgnoreCase(type)) {
          return resultSetType.getCode();
        }
      }
    }
    return NONE.code;
  }

  public int getCode() {
    return code;
  }
}
