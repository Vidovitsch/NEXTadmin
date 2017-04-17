/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Models.EventDay;
import java.util.ArrayList;

/**
 *
 * @author Arno Dekkers Los
 */
public interface IModDay {
    /**
     * Inserts a new day to firebase
     * 
     * @param day 
     */
    void insertDay(EventDay day);
    
    /**
     * Removes an existing day from firebase
     * 
     * @param day 
     */
    void removeDay(EventDay day);
    
    /**
     * Get all events from Firebase
     * 
     * @return All days
     */
    ArrayList<EventDay> getDays();
}
