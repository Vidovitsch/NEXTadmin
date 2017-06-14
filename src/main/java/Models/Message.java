/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used to keep load the data of a message in a accessible way
 * @author Bert
 */
public class Message {
    
    private int groupNumber;
    private String uid;
    private String content;
    private String date;
    private String userName;
    
    /**
     * This on of the constructors for this class it assigns the values of the 
     * parameters to the fields with the corresponding names
     * @param uid
     * @param userName
     * @param groupNumber
     * @param content
     * @param date 
     */
    public Message(String uid, String userName, int groupNumber, String content, String date)
    {
        this.groupNumber = groupNumber;
        this.uid = uid;
        this.content = content;
        this.date = date;
        this.userName = userName;
    }
    
    /**
     * This on of the constructors for this class it assigns the values of the 
     * parameters to the fields with the corresponding names
     * @param uid
     * @param groupNumber
     * @param content
     * @param date 
     */
    public Message(String uid, int groupNumber, String content, String date)
    {
        this.groupNumber = groupNumber;
        this.uid = uid;
        this.content = content;
        this.date = date;
    }
    
    /**
     * Get the value of groupNumber
     * 
     * @return te value of groupNumber
     */
    public int getGroupNumber() {
        return groupNumber;
    }
    
    /**
     * Set the value of groupNumber
     * 
     * @param groupNumber 
     */
    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
    
    /**
     * Get the value of pcn
     * 
     * @return The value of pcn
     */
    public String getUid()
    {
        return uid;
    }
    
    /**
     * Set the value of pcn
     * 
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }
    
    /**
     * Get the value of content
     * 
     * @return The value of content
     */
    public String getContent()
    {
        return content;
    }
    
    /**
     * Set the value of content
     * 
     * @param content 
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Get the value of date
     * 
     * @return The value of date
     */
    public String getDate()
    {
        return date;
    }
    
    /**
     * Set the value of date the method throws an exception if the given date
     * is not of the dd-MM-yyyy format
     * 
     * @param date 
     */
    public void setDate(String date) {
        try{
            Date dateEvent = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            this.date = date;
        } catch (ParseException ex){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " The date string had an invallid format. format should be dd-MM-yyyy");
        }
    }
    
    /**
     * Get the value of userName
     * 
     * @return The value of userName
     */
    public String getUserName()
    {
        return userName;
    }
    
    /**
     * Set the value of userName
     * 
     * @param userName 
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
