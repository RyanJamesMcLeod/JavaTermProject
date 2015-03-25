/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.forumBean;
import static database.credentials.getConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
public class forum implements Serializable{
    
    @EJB
    forumBean login;
    
    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(getResults("SELECT * FROM testchannel")).build();
    }
    
    @GET
    @Path("channels")
    @Produces("application/json")
    public Response getAllChannels() {
        return Response.ok(getChannels("SELECT * FROM channels ORDER BY channel_name")).build();
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
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
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
//            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return json;
//    }
}
