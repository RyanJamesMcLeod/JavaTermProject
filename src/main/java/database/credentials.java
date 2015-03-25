/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author Ryan
 */
public class credentials {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(credentials.class.getName()).log(Level.SEVERE, null, ex);
        }
        String hostname = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String portnum = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String pass = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        String jdbc = "jdbc:mysql://" + hostname + ":" + portnum + "/webstorm";
        return DriverManager.getConnection(jdbc, user, pass);
    }
}
