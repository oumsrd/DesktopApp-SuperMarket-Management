package com.example.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class DATABASE {
    Connection con;
    String url = "jdbc:mysql://localhost:3306/database1";
    public Connection Connect () {

        Statement stmt;

        String DatabaseName = "database1";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "sapnay");

            stmt = con.createStatement();
           // stmt.executeUpdate("DROP DATABASE " + DatabaseName);
           //int status = stmt.executeUpdate("CREATE DATABASE " + DatabaseName);
            stmt.executeUpdate("USE " + DatabaseName + ";");
            System.out.println("OK");
            /*
            if(status > 0) {
                System.out.println("Database is created successfully !!!");
                stmt.executeUpdate("DROP TABLE IF EXISTS fournisseur ");
               // stmt.executeUpdate("CREATE TABLE fournisseur ( "+"CodeFrnsr INT(10),"+"NomFrnsr VARCHAR(30),"+"AdresseFrnsr VARCHAR(30),"+"VilleFrnsr VARCHAR(30),"+"TelFrnsr VARCHAR(30)");

            }
            */

        } catch (
                SQLException e) {
            e.printStackTrace();
        } catch (
                ClassNotFoundException e) {

        }
        return con;
    }



    /*
    package ma.ensa.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    private Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public ConnexionDB() {
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecole?useSSL=false&allowPublicKeyRetrieval=true", "root", "Su572489");
           System.out.println("Connection OK");
       }catch (ClassNotFoundException ex){
           ex.printStackTrace();

       }catch (SQLException sqex){
           sqex.printStackTrace();
       }
    }
}


     */

}