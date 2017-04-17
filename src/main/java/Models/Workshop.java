/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Enums.EventType;
import java.util.ArrayList;


/**
 *
 * @author David
 */
public class Workshop extends Event {

    private ArrayList<User> users;
    private String presenter;
    private int maxUsers = 50;
    private int attendingUsers;
    private EventType eventType= EventType.Workshop;
    
    //For visual aspects
    private String hexColor = "#577F92";
    
    public Workshop(String eventName) {
        super(eventName);
    }
    
    /**
     * Get the value of eventType
     *
     * @return the value of eventType
     */
    @Override
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Set the value of eventType
     *
     * @param eventType new value of eventType
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Get the value of presenter
     *
     * @return the value of presenter
     */
    public String getPresenter() {
        return presenter;
    }

    /**
     * Set the value of presenter
     *
     * @param presenter new value of presenter
     */
    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }
    
    /**
     * Get the value of maxUsers
     *
     * @return the value of maxUsers
     */
    public int getMaxUsers() {
        return maxUsers;
    }

    /**
     * Set the value of maxUsers
     *
     * @param maxUsers new value of maxUsers
     */
    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }
    
    /**
     * get the value of users
     * 
     * @return users
     */
    public ArrayList<User> getUsers() {
        return users;
    }
    
    /**
     * set the value of users
     * 
     * @param users
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
        attendingUsers = users.size();
    }

    @Override
    public String getHexColor() {
        return hexColor;
    }

    @Override
    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }
    
    public int getAttendingUsers() {
        return attendingUsers;
    }

    public void setAttendingUsers(int attendingUsers) {
        this.attendingUsers = attendingUsers;
    }
}
