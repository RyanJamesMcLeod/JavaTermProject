/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.ForumBean;
import static database.Credentials.getConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ryan
 */
@Path("/login")
@SessionScoped
public class Login implements Serializable{
    
    /**
     * Bean that keeps track of the username and channelname throughout the
     * session
     */
    @Inject
    ForumBean login;

    /**
     * POST method that gets the information from the database based on the username and password consumed
     * @param json
     * @return Response.ok(jsonArray).build();
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getAll(JsonObject json) {
        JsonArray jsonArray = getResults("SELECT * FROM users WHERE username = ? AND password = ?", json.getString("username"), json.getString("password"));
        if (jsonArray.isEmpty())
            return Response.status(500).build();
        else {
            login.setUsername(json.getString("username"));
            return Response.ok(jsonArray).build();
        }
    }
    
    /**
     * POST method that inserts the information from the database based on the username and password consumed
     * @param json
     * @return Response.ok(json).build();
     */
    @POST
    @Path("join")
    @Produces("application/json")
    @Consumes("application/json")
    public Response joinForum(JsonObject json) {
        int result = newUser("INSERT INTO users (username, password) VALUES (?, ?)", json.getString("username"), json.getString("password"));
        if (result <= 0)
            return Response.status(500).build();
        else {
            return Response.ok(json).build();
        }
    }

    /**
     * This method will take in an sql string, perform a database call, then return the information in a JsonArray object
     * with the username and password found
     * @param sql
     * @param params
     * @return json
     */
    public static JsonArray getResults(String sql, String... params) {
        JsonArray json = null;
        try {
            JsonArrayBuilder array = Json.createArrayBuilder();
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                array.add(Json.createObjectBuilder()
                        .add("username", rs.getString("username"))
                        .add("password", rs.getString("password")));
            }
            conn.close();
            json = array.build();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    /**
     * This method will take in an sql string, perform a database call, then return back to the main method, as this
     * method only inserts an entry into the database
     * @param sql
     * @param params
     * @return 
     */
    public static int newUser(String sql, String... params) {
        int result = -1;
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            result = pstmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
