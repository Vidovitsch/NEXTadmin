/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Models.Group;
import Models.Message;
import Models.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public interface IModGroup {
    
    /**
     * Adds a member to a group
     * 
     * @param group
     * @param user 
     */
    void addUser(Group group, User user);
    
    /**
     * Fetch all groups from firebase
     * 
     * @return All groups
     */
    ArrayList<Group> getGroups();
    
    /**
     * Removes all groups from the firebase and resets the group field
     * for all the users
     */
    void resetGroups();
    
    /**
     * This method returns the next ID that has to be used when creating a new group
     */
    int getHighestID();
    
    /**
     * This method is used to add new user groups with their users to the DB
     * @param newGroups 
     */
    void addNewGroupsWithUsers(ArrayList<Group> newGroups);
}
