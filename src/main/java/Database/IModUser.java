/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Models.User;

/**
 *
 * @author David
 */
public interface IModUser {

    /**
     * Adds a new user into Firebase
     * 
     * @param user 
     */
    void insertUser(User user);
    
    /**
     * Removes an user from Firebase
     * 
     * @param user 
     */
    void removeUser(User user);
    
    /**
     * Fetch a user from Firbase with a pcn
     * 
     * @param pcn
     * @return User
     */
    User getUser(String pcn);
}
