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
public class Lecture extends Event {
    
    private String presenter;
    private EventType eventType = EventType.Lecture;

    //For visual aspects
    private String hexColor = "#F6B067";
    
    public Lecture(String eventName) {
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
    
    /**
     * Get the value of presenter
     *
     * @return the value of presenter
     */
    public String getPresenter() {
        return presenter;
    }

    /**
     * Set the value of presenter
     *
     * @param presenter new value of presenter
     */
    public void setPresenter(String presenter) {
        this.presenter = presenter;
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
