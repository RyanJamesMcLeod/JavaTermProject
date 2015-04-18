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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ryan
 */
@Path("/forum")
@SessionScoped
public class Forum implements Serializable {

    /**
     * Bean that keeps track of the username and channelname throughout the
     * session
     */
    @Inject
    ForumBean login;

    /**
     * Method that GETs all the information for all the forum posts, then builds
     * the call from the response
     *
     * @return Response.ok(getResults(SQLString)).build();
     */
    @GET
    @Produces("application/json")
    public Response getAll() {
        String param = login.getChannelname();
        String SQLString = "SELECT * FROM " + param;
        return Response.ok(getResults(SQLString)).build();
    }

    /**
     * Method that GETs all the information for all the channels in the
     * database, then builds the call from the response
     *
     * @return Response.ok(getChannels("SELECT * FROM channels")).build();
     */
    @GET
    @Path("channels")
    @Produces("application/json")
    public Response getAllChannels() {
        return Response.ok(getChannels("SELECT * FROM channels")).build();
    }

    /**
     * Inserts into the database the information it takes in as a forum post
     *
     * @param json
     * @return Response.ok(json).build();
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response postToForum(JsonObject json) {
        String param = login.getChannelname();
        String user = login.getUsername();
        String SQLString = "INSERT INTO " + param + "(username, date, information) VALUES ('" + user + "', NOW(), '" + json.getString("information") + "')";
        int result = forumPost(SQLString);
        if (result <= 0) {
            return Response.status(500).build();
        } else {
            return Response.ok(json).build();
        }
    }

    /**
     * Method that makes a new channel, based on the channel name that is given.
     * First it inserts into the channels table (the one that holds all the
     * names of the channels Second, it will create a new table that is
     * representative of that channel Finally, it creates a first post for that
     * database for the data to be read
     *
     * @param json
     * @return Response.ok(json).build();
     */
    @POST
    @Path("newChannel")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createChannel(JsonObject json) {
        String channelname = json.getString("channelname");
        String SQLString = "INSERT INTO channels (channel_name) VALUES ('" + channelname + "')";
        int result = channelPost(SQLString);
        if (result == 1) {
            login.setChannelname(channelname);
            SQLString = "CREATE TABLE " + json.getString("channelname") + " (channel_id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), date DATETIME, information VARCHAR(255))";
            result = forumPost(SQLString);
            String param = login.getChannelname();
            String user = login.getUsername();
            SQLString = "INSERT INTO " + param + " (username, date, information) VALUES (\"" + user + "\", NOW(),\"This is the first post\")";
            result = channelPost(SQLString);
        }
        if (result <= 0) {
            return Response.status(500).build();
        } else {
            return Response.ok(json).build();
        }
    }

    /**
     * This method will switch the channel bean name to whatever channelname is
     * consumed to this method
     *
     * @param json
     * @return Response.ok(getChannels("SELECT * FROM channels")).build();
     */
    @POST
    @Path("switch")
    @Produces("application/json")
    @Consumes("application/json")
    public Response switchChannels(JsonObject json) {
        login.setChannelname(json.getString("channelname"));
        return Response.ok(getChannels("SELECT * FROM channels")).build();
    }

    /**
     * Takes in a SQL string, and converts that information into a JsonObject
     * that is returned to the main method to be built
     *
     * @param sql
     * @param params
     * @return json;
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
                        .add("channel_id", rs.getString("channel_id"))
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

    /**
     * DELETE method that will delete information from the database
     *
     * @param id
     * @return Response.ok().build();
     */
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            String param = login.getChannelname();
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + param + " WHERE channel_id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Forum.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }

    /**
     * Method that will get the channel name and return it in a JsonObject to be
     * used later
     *
     * @param sql
     * @param params
     * @return json;
     */
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

    /**
     * Method used to get information inserted into the forum channel
     *
     * @param sql
     * @param params
     * @return result;
     */
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

    /**
     * Method used to get information posted into a specific channel
     *
     * @param sql
     * @return result;
     */
    public static int channelPost(String sql) {
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
}
