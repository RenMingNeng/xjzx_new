package com.anxuan.xjzx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
  private static Connection conn = null;
  
  public static Connection getConn()
  {
    String driver = "com.mysql.jdbc.Driver";
    try
    {
      Class.forName(driver);
      //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xjzx_new_test?useUnicode=true&amp;characterEncoding=utf-8", "root", "asd2011!#%246");
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xjzx_new_test?useUnicode=true&amp;characterEncoding=utf-8", "root", "123456");
      conn.setAutoCommit(false);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return conn;
  }
  
  public static void closeConn()
  {
    try
    {
      if (null != conn) {
        conn.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}
