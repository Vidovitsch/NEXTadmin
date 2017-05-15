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
public class StudentManagerViewModel {

    private ArrayList<String> groups;
    private String mailSelected = null;
    private String workshopSelected = null;
    private String groupSelected = null;

    public String getMailSelected() {
        return mailSelected;
    }

    public void setMailSelected(String mailSelected) {
        this.mailSelected = mailSelected;
    }

    public String getWorkshopSelected() {
        return workshopSelected;
    }

    public void setWorkshopSelected(String workshopSelected) {
        this.workshopSelected = workshopSelected;
    }

    public String getGroupSelected() {
        return groupSelected;
    }

    public void setGroupSelected(String groupSelected) {
        this.groupSelected = groupSelected;
    }
    
    public ArrayList<String> getGroups() {
        return this.groups;
    }
    
    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }
}
