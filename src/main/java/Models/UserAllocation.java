/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Database.DBGroupModifier;
import Database.IModGroup;
import Enums.Course;
import java.awt.List;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is used to allocate all users that have signed up for the event
 * into different nuser groups. this is called when the event is about to start
 * to create the initial groups
 * @author David
 */

public class UserAllocation {
    private DBGroupModifier groupModifier;
    private boolean mixCourses = true;
    private int maxUsers = 10;
    HashMap<Course, ArrayList<User>> unassignedUsers;
    private int nextID = -1;
    
    /**
     * This is the constructor of the UserAllocation class
     * it takes a list of the user that it has to put into groups
     * and will call the methods required to accomplish this
     * @param usersToAssign 
     */
    public UserAllocation(ArrayList<User> usersToAssign){
        groupModifier = new DBGroupModifier();
        this.toHashMap(usersToAssign);
        this.nextID = groupModifier.getHighestID();
        System.out.println("My next ID equals " + nextID);
        allocateUsers();
    }
    
    /**
     * This method converts the List<User< into an HashMap<Course, List<User>>
     * The map is a more usable format for the user allocation
     * @param usersToCast
     * @return unassignedUsers;
     */
    private HashMap<Course, ArrayList<User>> toHashMap(ArrayList<User> usersToCast){
        unassignedUsers = new HashMap<Course, ArrayList<User>>();
        for(User u : usersToCast){
            ArrayList users;
            if(unassignedUsers.containsKey(u.getCourse())){
                users = unassignedUsers.get(u.getCourse());
            }else{
                users = new ArrayList<User>();
            }
            users.add(u);
            unassignedUsers.put(u.getCourse(), users);
        }
        return unassignedUsers;
    }
    
    /**
     * This method allocates the users into the groups
     * it considers the value of mixCourses when allocating
     * in the case that this is true it combines all the users into 1 list
     * that is sorted primarelly by the users Course and secondaire by their semester
     * and calls the putUsersInGroup method for the total list
     * if it is false it calls the putUserInGroup method for each ArrayList<User> in
     * unassignedUsers
     */
    private void allocateUsers(){
        if(mixCourses){
            ArrayList<User> toAllocate = new ArrayList<User>();
            for(ArrayList<User> users : unassignedUsers.values()){
                Collections.sort(users);
                toAllocate.addAll(users);
            }
            putUsersInGroup(toAllocate);
        }else{
            for(ArrayList<User> users : unassignedUsers.values()){
                Collections.sort(users);
                putUsersInGroup(users);
            }
        }
    }
    
    /**
     * This method puts the generates the new user groups by calling the createNewGroups
     * method using the toAllocate.size
     * it divides the toAllocate users in these groups with the divideUsersOverGroups method
     * @param toAllocate 
     */
    private void putUsersInGroup(ArrayList<User> toAllocate){
        ArrayList<Group> newGroups = createNewGroups((int) Math.ceil(toAllocate.size() / 10.0));
        divideUsersOverGroups(newGroups, toAllocate);
    }
    
    /**
     * This method creates new groups it uses the nextID to determine the goups
     * ID and the count variable to determine how many groups to create
     * @param count
     * @return newGroups
     */
    private ArrayList<Group> createNewGroups(int count){
        ArrayList<Group> newGroups = new ArrayList<Group>();
        for(int i = 0 ; i < count ; i++ ){
            Group newGroup = new Group(Integer.toString(nextID));
            newGroup.setGroupName("Group" + Integer.toString(nextID));
            newGroups.add(newGroup);
            nextID++;
        }
        return newGroups;
    }
    
    /**
     * This method divides the toAllocate users over the newGroups
     * it loops through the users and adds the user to the List in
     * order
     * @param newGroups
     * @param toAllocate 
     */
    private void divideUsersOverGroups(ArrayList<Group> newGroups, ArrayList<User> toAllocate){
        int groupIndex = 0;
        for(User u: toAllocate){
            Group group = newGroups.get(groupIndex % newGroups.size());
            ArrayList users = group.getUsers();
            users.add(u);
            groupIndex++;
        }
        groupModifier.addNewGroupsWithUsers(newGroups);
    }
}
