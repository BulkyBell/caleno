package com.caleno.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            //TODO: comprobar como conectarlo desde una maquina diferente a la maquina virtual
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/caleno", "root", "");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

}
