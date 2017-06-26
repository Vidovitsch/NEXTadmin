/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Models.User;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the interface used by the DBUserModifier
 * @author Arno Dekkers Los
 */
public interface IModUsers {
    /**
     * This method retrieves a list of users that are students and are not 
     * assigned a group yet.
     * 
     * @return List<User>
     */
    ArrayList<User> getUnassignedUsers();
}
