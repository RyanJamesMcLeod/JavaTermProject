/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 * Bean that is used to store the username and channel name that will be saved as the user's session continues
 * @author Ryan
 */
@SessionScoped
public class ForumBean implements Serializable {
    
    String username = "";
    String channelname = "testchannel";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }
    
}
