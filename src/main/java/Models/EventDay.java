/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Enums.EventType;

/**
 *
 * @author Arno Dekkers Los
 */
public class EventDay extends EventDate {
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
}
