/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Enums.EventType;


/**
 *
 * @author David
 */
public class Performance extends Event {

    private EventType eventType = EventType.Performance;
    
    //For visual aspects
    private String hexColor = "#A2B9B2";
    
    public Performance(String eventName) {
        super(eventName);
    }
    
    /**
     * Get the value of eventType
     *
     * @return the value of eventType
     */
    @Override
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Set the value of eventType
     *
     * @param eventType new value of eventType
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    
    @Override
    public String getHexColor() {
        return hexColor;
    }

    @Override
    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }
}
