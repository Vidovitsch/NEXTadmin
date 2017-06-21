/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Enums.EventType;

/**
 * This class is used by scheduled school days. so the regular schedule that every student attentds
 * @author Arno Dekkers Los
 */
public class EventDay extends EventDate {
    private String id;
    
    /**
     * this is the constructor of EventDay it passes the given parameter to the parent
     * @param eventName 
     */
    public EventDay(String eventName){super(eventName);}
    
    /**
     * Get the value of eventType
     *
     * @return the value of eventType
     */
    @Override
    public EventType getEventType() {
        return EventType.None;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getId(){
        return id;
    }
}
