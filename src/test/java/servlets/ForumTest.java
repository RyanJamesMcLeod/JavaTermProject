/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ryan
 */
public class ForumTest {
    
    public ForumTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAll method, of class Forum.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        Forum instance = new Forum();
        Response expResult = null;
        Response result = instance.getAll();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getAllChannels method, of class Forum.
     */
    @Test
    public void testGetAllChannels() {
        System.out.println("getAllChannels");
        Forum instance = new Forum();
        Response expResult = null;
        Response result = instance.getAllChannels();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of postToForum method, of class Forum.
     */
    @Test
    public void testPostToForum() {
        System.out.println("postToForum");
        JsonObject json = null;
        Forum instance = new Forum();
        Response expResult = null;
        Response result = instance.postToForum(json);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of createChannel method, of class Forum.
     */
    @Test
    public void testCreateChannel() {
        System.out.println("createChannel");
        JsonObject json = null;
        Forum instance = new Forum();
        Response expResult = null;
        Response result = instance.createChannel(json);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of switchChannels method, of class Forum.
     */
    @Test
    public void testSwitchChannels() {
        System.out.println("switchChannels");
        JsonObject json = null;
        Forum instance = new Forum();
        Response expResult = null;
        Response result = instance.switchChannels(json);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getResults method, of class Forum.
     */
    @Test
    public void testGetResults() {
        System.out.println("getResults");
        String sql = "";
        String[] params = null;
        JsonArray expResult = null;
        JsonArray result = Forum.getResults(sql, params);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of delete method, of class Forum.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        int id = 0;
        Forum instance = new Forum();
        Response expResult = null;
        Response result = instance.delete(id);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getChannels method, of class Forum.
     */
    @Test
    public void testGetChannels() {
        System.out.println("getChannels");
        String sql = "";
        String[] params = null;
        JsonArray expResult = null;
        JsonArray result = Forum.getChannels(sql, params);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of forumPost method, of class Forum.
     */
    @Test
    public void testForumPost() {
        System.out.println("forumPost");
        String sql = "";
        String[] params = null;
        int expResult = 0;
        int result = Forum.forumPost(sql, params);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of channelPost method, of class Forum.
     */
    @Test
    public void testChannelPost() {
        System.out.println("channelPost");
        String sql = "";
        int expResult = 0;
        int result = Forum.channelPost(sql);
        assertEquals(expResult, result);
        
    }
    
}
