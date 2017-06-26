/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * This model is used to fetch the user input from the index screen when creating new
 * User groups. it has a field for studentsPerGroup and mixGroups
 * @author Arno Dekkers Los
 */
public class IndexModel {
    private int studentsPerGroup;
    private boolean mixGroups;

    public int getStudentsPerGroup() {
        return studentsPerGroup;
    }

    public void setStudentsPerGroup(int studentsPerGroup) {
        this.studentsPerGroup = studentsPerGroup;
    }

    public boolean getMMixGroups() {
        return mixGroups;
    }

    public void setMixGroups(boolean mixGroups) {
        this.mixGroups = mixGroups;
    }
}
