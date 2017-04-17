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

    private int groupNumber;
    private String groupName;
    private ArrayList<User> users;
    private ArrayList<Message> messages;
    private int location = 0;
        
    public Group(int groupNumber) {
        this.groupNumber = groupNumber;
        users = new ArrayList();
    }
    
    /**
     * Get the value of groupNumber
     *
     * @return the value of groupNumber
     */
    public int getGroupNumber() {
        return groupNumber;
    }

    /**
     * Set the value of groupNumber
     *
     * @param groupNumber new value of groupNumber
     */
    public void setGroupNumber(int groupNumber) {
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
    
    public int getLocation() {
        return location;
    }

    public void setLocation(int groupLocation) {
        this.location = location;
    }
}
