/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

/**
 *
 * @author David
 */
public abstract class Event extends EventDate {
    private String imageURL = "/images/default_workshop.jpg";
    private String id;

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
    
    public abstract String getHexColor();
    
    public abstract void setHexColor(String hexColor);
}
