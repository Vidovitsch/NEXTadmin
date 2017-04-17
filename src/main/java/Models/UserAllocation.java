/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Database.DBGroupModifier;
import Database.IModGroup;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author David
 */

public class UserAllocation {

    private final static int MAX_MEMBERS = 10;
    private IModGroup groupMod = new DBGroupModifier();
    private User user = null;
    private ArrayList<Group> groups;

    public UserAllocation(User user) {
        this.user = user;
        this.groups = groupMod.getGroups();
    }

    /**
     * Allocates a user to a group
     */
    public void allocate() {
        groups = getNonFullGroups();
        Map<Group, Double> scores = new HashMap();
        for (Group group : groups) {
            Map.Entry<Group, Double> score = calcScore(group);
            scores.put(score.getKey(), score.getValue());
        }
        Group group = getPreferredGroup(scores);
        groupMod.addUser(group, user);
    }
    
    /**
     * Get a list of groups with room left for new members
     * 
     * @return A list of non-empty groups
     */
    private ArrayList<Group> getNonFullGroups() {
        ArrayList<Group> nonFullGroups = new ArrayList();
        //Filter the original list of groups on member size below the max
        for (Group group : groups) {
            if (group.getUsers().size() < MAX_MEMBERS) {
                nonFullGroups.add(group);
            }
        }
        return nonFullGroups;
    }
    
    /**
     * Calculate the score of a group in which the user can be allocated
     * Rules:
     * Every semester represents points. (S1 = 1 point, S2 = 2 points, etc.).
     * The avarage is calculated with a sum of the three courses the
     * current user isn't following (this is callled the baseLine).
     * At last the end score is the value of your own course + the user's current semester
     * - the baseLine.
     * 
     * @param group
     * @return A group with a score for that group
     */
    private Map.Entry<Group, Double> calcScore(Group group) {
        ArrayList<User> users = group.getUsers();
        int m = 0;
        int s = 0;
        int t = 0;
        int b = 0;
        //Calcule the value of each course
        for (User u : users) {
            switch (u.getCourse()) {
                case Media_Design:
                    m += u.getSemester();
                    break;
                case Software_Engineering:
                    s += u.getSemester();
                    break;
                case Techniek:
                    t += u.getSemester();
                    break;
                default:
                    b += u.getSemester();
            }
        }
        double baseLine = 0;
        double score = 0;
        //Cacluate the baseLine of three couses combined (exc. user's course).
        switch (user.getCourse()) {
            case Media_Design:
                    baseLine = (s + t + b) / 3;
                    score = m + user.getSemester() - baseLine;
                    break;
                case Software_Engineering:
                    baseLine = (m + t + b) / 3;
                    score = s + user.getSemester() - baseLine;
                    break;
                case Techniek:
                    baseLine = (s + m + b) / 3;
                    score = t + user.getSemester() - baseLine;
                    break;
                default:
                    baseLine = (s + t + m) / 3;
                    score = b + user.getSemester() - baseLine;
        }
        
        Map.Entry<Group, Double> entry = new AbstractMap.SimpleEntry<Group, Double>(group, Math.abs(score));
        return entry;
    }

    //This method can be changes if additional factors (like the importance of the
    //group members left) will be added to the algorithm.
    private Group getPreferredGroup(Map<Group, Double> data) {
        Group group = null;
        Map<Double, Group> sortedMap = new TreeMap();
        for (Map.Entry<Group, Double> entry : data.entrySet()) {
            sortedMap.put(entry.getValue(), entry.getKey());
        }
        for (Map.Entry<Double, Group> entry : sortedMap.entrySet()) {
            group = entry.getValue();
        }
        return group;
    }
}
