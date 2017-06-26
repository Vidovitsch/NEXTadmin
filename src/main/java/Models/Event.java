/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

/**
 * This is the parent class for scheduleable activities that are not the basic school days
 * this class extends EventDate
 * @author David
 */
public abstract class Event extends EventDate {
    private String imageURL = "/images/default_workshop.jpg";
    private String id;

    /**
     * this is the constructor of this abstract class. it takes an eventName
     * which it passes on to the parent class eventDate
     * @param eventName 
     */
    public Event(String eventName) { super(eventName); }

    /**
     * Get the value of imageURL
     *
     * @return the value of imageURL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Set the value of imageURL
     *
     * @param imageURL new value of imageURL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * this is an abstract method that will contain the color associated with the
     * event so it can be displayed correcly in the UI
     * @return hexColor
     */
    public abstract String getHexColor();
    
    /**
     * abstract metod used to set the hexcode for an event
     * @param hexColor 
     */
    public abstract void setHexColor(String hexColor);
}
