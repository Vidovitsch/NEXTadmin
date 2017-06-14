/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import java.util.ArrayList;


/**
 * This class is used to pass the information between the business layer and the GUI
 * this model is specifically used by the Management screen
 * @author David
 */
public class StudentManagerViewModel {

    private ArrayList<String> groups;
    private ArrayList<String> workshops;
    private String mailSelected = null;
    private String workshopSelected = null;
    private String groupSelected = null;

    /**
     * Get the value of mailSelected
     * @return the value of mailSelected
     */
    public String getMailSelected() {
        return mailSelected;
    }

    /**
     * Set the value of mailSelected
     * @param mailSelected new value of mailSelected
     */
    public void setMailSelected(String mailSelected) {
        this.mailSelected = mailSelected;
    }

    /**
     * Get the value of workshopSelected
     * @return the value of workshopSelected
     */
    public String getWorkshopSelected() {
        return workshopSelected;
    }

    /**
     * Set the value of workshopSelected
     * @param workshopSelected new value of workshopSelected
     */
    public void setWorkshopSelected(String workshopSelected) {
        this.workshopSelected = workshopSelected;
    }

    /**
     * Get the value of groupSelected
     * @return the value of groupSelected
     */
    public String getGroupSelected() {
        return groupSelected;
    }

    /**
     * Set the value of groupSelected
     * @param groupSelected new value of groupSelected
     */
    public void setGroupSelected(String groupSelected) {
        this.groupSelected = groupSelected;
    }
    
    /**
     * Get the value of groups
     * @return the value of groups
     */
    public ArrayList<String> getGroups() {
        return this.groups;
    }
    
    /**
     * Set the value of groups
     * @param groups new value of groups
     */
    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }
    
    /**
     * Get the value of workshops
     * @return the value of workshops
     */
    public ArrayList<String> getWorkshops() {
        return this.workshops;
    }
    
    /**
     * Set the value of workshops
     * @param workshops new value of workshops
     */
    public void setWorkshops(ArrayList<String> workshops) {
        this.workshops = workshops;
    }
}
