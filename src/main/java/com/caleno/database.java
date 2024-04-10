package com.caleno;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //TODO: comprobar como conectarlo desde una maquina diferente a la maquina virtual
            Connection connect = DriverManager.getConnection("jdbc:mysql://192.168.1.254:8080/caleno", "root", "");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

}
