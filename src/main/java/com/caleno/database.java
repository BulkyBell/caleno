package com.caleno;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://192.168.1.254:3306/caleno", "root", "");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

}
