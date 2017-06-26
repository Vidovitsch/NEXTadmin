/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * This class is used to load a scheduled object into the GUI using minimal data
 * @author Arno Dekkers Los
 */
public class ScheduledItemModel {
    private String id;
    private String string;

    /**
     * a constructor for ScheduledItemModel this is an emtpy constructor
     */
    public ScheduledItemModel(){}
    
    /**
     * a constructor for ScheduledItemModel. It saves the given parameters to the
     * fields with the corresponding name
     * @param id
     * @param string 
     */
    public ScheduledItemModel(String id, String string) {
        this.id = id;
        this.string = string;
    }

    /**
     * In the html code special characters like : give trouble loading the page,
     * unless the string is surounded by '' this method takes a string and returns 'string'
     * @param s
     * @return 's'
     */
    public String AddSpecialChars(String s) {
        return "'" + s + "'";
    }
    
    /**
     * Get the value of id
     * @return the value of id
     */
    public String getId() {
        return id;
    }
    
    /**
     * Set the value of id
     * @param id new value of id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the value of string
     * @return the value of string
     */
    public String getString() {
        return string;
    }

    /**
     * Set the value of string
     * @param string new value of string
     */
    public void setString(String string) {
        this.string = string;
    }
}
