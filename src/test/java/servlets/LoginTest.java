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
public class LoginTest {
    
    public LoginTest() {
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
     * Test of getAll method, of class Login.
     */
    @org.junit.Test
    public void testGetAll() {
        System.out.println("getAll");
        JsonObject json = null;
        Login instance = new Login();
        Response expResult = null;
        Response result = instance.getAll(json);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of joinForum method, of class Login.
     */
    @org.junit.Test
    public void testJoinForum() {
        System.out.println("joinForum");
        JsonObject json = null;
        Login instance = new Login();
        Response expResult = null;
        Response result = instance.joinForum(json);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getResults method, of class Login.
     */
    @org.junit.Test
    public void testGetResults() {
        System.out.println("getResults");
        String sql = "";
        String[] params = null;
        JsonArray expResult = null;
        JsonArray result = Login.getResults(sql, params);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of newUser method, of class Login.
     */
    @org.junit.Test
    public void testNewUser() {
        System.out.println("newUser");
        String sql = "";
        String[] params = null;
        int expResult = 0;
        int result = Login.newUser(sql, params);
        assertEquals(expResult, result);
        
    }
    
}
