/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * This model is used between the business layer and GUI to load/sent data
 * that have to do with a EventDate
 * @author Arno Dekkers Los
 */
public class ScheduleableItemModel {

    private String id;
    private String eventName = "-1";
    private String startTime = "-1";
    private String endTime = "-1";
    private String date = "-1";
    private String day = "-1";
    private String locationName = "-1";
    private String description = "-1";
    private String presenter = "-1";
    private String imageURL = "-1";
    private String maxUsers = "-1";
    private String type = "-1";

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
     * Get the value of eventName
     * @return the value of eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Set the value of eventName
     * @param eventName new value of eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Get the value of startTime
     * @return the value of startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Set the value of startTime
     * @param startTime new value of startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Get the value of endTime
     * @return the value of endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Set the value of endTime
     * @param endTime new value of endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Get the value of date
     * @return the value of date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the value of date
     * @param date new value of date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the value of day
     * @return the value of day
     */
    public String getDay() {
        return day;
    }

    /**
     * Set the value of day
     * @param day new value of day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Get the value of locationName
     * @return the value of locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Set the value of locationName
     * @param locationName new value of locationName
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * Get the value of description
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of presenter
     * @return the value of presenter
     */
    public String getPresenter() {
        return presenter;
    }

    /**
     * Set the value of presenter
     * @param presenter new value of presenter
     */
    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    /**
     * Get the value of imageURL
     * @return the value of imageURL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Set the value of imageURL
     * @param imageURL new value of imageURL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Get the value of maxUsers
     * @return the value of maxUsers
     */
    public String getMaxUsers() {
        return maxUsers;
    }

    /**
     * Set the value of maxUsers
     * @param maxUsers new value of maxUsers
     */
    public void setMaxUsers(String maxUsers) {
        this.maxUsers = maxUsers;
    }

    /**
     * Get the value of type
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of type
     * @param type new value of type
     */
    public void setType(String type) {
        this.type = type;
    }
}
