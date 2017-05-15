/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import java.util.ArrayList;


/**
 *
 * @author David
 */
public class Group {

    private String groupNumber;
    private String groupName;
    private ArrayList<User> users;
    private ArrayList<Message> messages;
    private int location = 0;
        
    public Group(String groupNumber) {
        this.groupNumber = groupNumber;
        users = new ArrayList();
    }
    
    /**
     * Get the value of groupNumber
     *
     * @return the value of groupNumber
     */
    public String getGroupNumber() {
        return groupNumber;
    }

    /**
     * Set the value of groupNumber
     *
     * @param groupNumber new value of groupNumber
     */
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }
    
    /**
     * Get the value of groupName
     *
     * @return the value of groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Set the value of groupName
     *
     * @param groupName new value of groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Get the value of users
     * 
     * @return the value of users
     */
    public ArrayList<User> getUsers() {
        return users;
    }
    
    /**
     * Set the value of users
     * 
     * @param users 
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
    /**
     * Get the value of messages
     * 
     * @return messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }
    
    /**
     * Set the value of messages
     * 
     * @param messages 
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    
    /**
     * Get the value of the group location
     * 
     * @return group location (table number)
     */
    public int getLocation() {
        return location;
    }

    /**
     * Set the value of messages
     * 
     * @param groupLocation
     */
    public void setLocation(int groupLocation) {
        this.location = groupLocation;
    }
}
