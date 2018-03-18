package com.geoximity.dao;


   import java.sql.*;
   import java.util.*;


   public class ConnectionManager {

      static Connection con;
      static String url;

      public static Connection getConnection()
      {
         try
         {
            String url = "jdbc:postgresql://codetallydb.ch43urvkgzuk.ca-central-1.rds.amazonaws.com:5432/codetally";
            // assuming "DataSource" is your DataSource name

            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Class.forName("org.postgresql.Driver");
            try
            {
               con = DriverManager.getConnection(url,"codetally","!72Hockey");
            // assuming your SQL Server's	username is "username"
            // and password is "password"
            }
            catch (SQLException ex)
            {
               ex.printStackTrace();
            }
         }

         catch(ClassNotFoundException e)
         {
            System.out.println(e);
         }

      return con;
}
   }

