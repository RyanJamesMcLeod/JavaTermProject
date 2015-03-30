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
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ryan
 */
@Path("/forum")
@SessionScoped
public class Forum implements Serializable{
    
    @Inject
    ForumBean login;
    
    @GET
    @Produces("application/json")
    public Response getAll() {
        String param = login.getChannelname();
        String SQLString = "SELECT * FROM " + param;
        return Response.ok(getResults(SQLString)).build();
    }
    
    @GET
    @Path("channels")
    @Produces("application/json")
    public Response getAllChannels() {
        return Response.ok(getChannels("SELECT * FROM channels")).build();
    }
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response postToForum(JsonObject json) {
        String param = login.getChannelname();
        String user = login.getUsername();
        String SQLString = "INSERT INTO " + param + "(username, date, information) VALUES ('" + user + "', NOW(), '" + json.getString("information") + "')";
        int result = forumPost(SQLString);
        if (result <= 0)
            return Response.status(500).build();
        else {
            return Response.ok(json).build();
        }
    }
    
    @POST
    @Path("newChannel")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createChannel(JsonObject json) {
        String channelname = json.getString("channelname");
        String SQLString = "INSERT INTO channels (channel_name) VALUES ('" + channelname + "')";
        int result = channelPost(SQLString);
        if (result == 1) {
            SQLString = "CREATE TABLE " + channelname + " (channel_id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), date DATETIME, information VARCHAR(255))";
            result = channelPost(SQLString);
            if (result == 1) {
            String param = login.getChannelname();
            String user = login.getUsername();
            SQLString = "INSERT INTO " + param + "(username, date, information) VALUES ('" + user + "', NOW(), 'This is the first post')";
            result = channelPost(SQLString);
            }
        }
        if (result <= 0)
            return Response.status(500).build();
        else {
            login.setChannelname(channelname);
            return Response.ok(json).build();
        }
    }
    
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
                        .add("date", rs.getString("date"))
                        .add("information", rs.getString("information")));
            }
            conn.close();
            json = array.build();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    public static JsonArray getChannels(String sql, String... params) {
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
                        .add("channel_name", rs.getString("channel_name")));
            }
            conn.close();
            json = array.build();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    public static int forumPost(String sql, String... params) {
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
    
    public static int channelPost(String sql, String... params) {
        int result = -1;
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            result = pstmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
//    @POST
//    @Produces("application/json")
//    @Consumes("application/json")
//    public Response getAll(JsonObject json) {
//        JsonArray jsonArray = getResults("SELECT * FROM testchannel");
//        if (jsonArray.isEmpty())
//            return Response.status(500).build();
//        else
//            return Response.ok(jsonArray).build();
//
//    }
//
//    public static JsonArray getResults(String sql, String... params) {
//        JsonArray json = null;
//        try {
//            JsonArrayBuilder array = Json.createArrayBuilder();
//            Connection conn = getConnection();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            for (int i = 0; i < params.length; i++) {
//                pstmt.setString(i + 1, params[i]);
//            }
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                array.add(Json.createObjectBuilder()
//                        .add("username", rs.getString("username"))
//                        .add("password", rs.getString("password")));
//            }
//            conn.close();
//            json = array.build();
//        } catch (SQLException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return json;
//    }
}
