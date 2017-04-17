/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Enums.EventType;
import Models.Event;
import Models.Lecture;
import Models.Performance;
import Models.User;
import Models.Workshop;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author David
 */
public class DBEventModifier implements IModEvent {
    
    private static Firebase firebase;
    private Object lock;
    private boolean done = false;

    public DBEventModifier() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (Firebase) connector.getConnectionObject();
    }

    @Override
    public void addAttendingUser(Workshop event, User user) {
        Firebase ref = firebase.child("Event").child(event.getId()).child("Attending").child(user.getUid());
        ref.setValue("Attending");
    }
    
    @Override
    public void removeAttendingUser(Workshop event, User user) {
        Firebase ref = firebase.child("Event").child(event.getId()).child("Attending").child(user.getUid());
        ref.removeValue();
    }
    
    @Override
    public void insertEvent(Event event) {
        Map<String, String> data = new HashMap();
        data.put("EventName", event.getEventName());
        data.put("StartTime", event.getStartTime());
        data.put("EndTime", event.getEndTime());
        data.put("Date", event.getDate());
        data.put("ImageURL", event.getImageURL());
        System.out.println("image url: " + event.getImageURL());
        data.put("LocationName", event.getLocationName());
        data.put("Description", event.getDescription());
        data = putEventTypeValues(event, data);
        if(event.getEventType() == EventType.Workshop){
            data.put("maxUsers", String.valueOf(((Workshop)event).getMaxUsers()));
        }
        Firebase ref = firebase.child("Event").push();
        ref.setValue(data);
    }
    
    @Override
    public void removeEvent(Event event) {
        Firebase ref = firebase.child("Event").child(event.getId());
        ref.removeValue();
    }

    @Override
    public ArrayList<Event> getEvents() {
        final ArrayList<Event> events = new ArrayList();
        Firebase ref = firebase.child("Event");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String id = ds.getKey();
                    String startTime = (String) ds.child("StartTime").getValue();
                    String endTime = (String) ds.child("EndTime").getValue();
                    String date = (String) ds.child("Date").getValue();
                    String imageURL = (String) ds.child("ImageURL").getValue();
                    String locationName = (String) ds.child("LocationName").getValue();
                    String description = (String) ds.child("Description").getValue();
                    
                    Event event = specifyEvent(ds);
                    event.setId(id);
                    event.setStartTime(startTime);
                    event.setEndTime(endTime);
                    event.setDate(date);
                    event.setImageURL(imageURL);
                    event.setLocationName(locationName);
                    event.setDescription(description);
                    
                    events.add(event);
                }
                unlockFXThread();
            }
            
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println(fe.toException().toString());
            }
        });
        
        lockFXThread();
        return events;
    }
    
    private Event specifyEvent(DataSnapshot ds) {
        String eventType = (String) ds.child("EventType").getValue();
        if (eventType.equals("Workshop")) {
            Workshop event = new Workshop((String) ds.child("EventName").getValue());
            String presenter = (String) ds.child("Presenter").getValue();
            int maxUsers = Integer.valueOf((String) ds.child("MaxUsers").getValue());
            ArrayList<User> users = attendantsToUsers(ds.child("Attending"));
            
            event.setPresenter(presenter);
            event.setMaxUsers(maxUsers);
            event.setUsers(users);
            
            return event;
        } else if (eventType.equals("Lecture")) {
            Lecture event = new Lecture((String) ds.child("EventName").getValue());
            String presenter = (String) ds.child("Presenter").getValue();
            event.setPresenter(presenter);
            
            return event;
        } else {
            Performance event = new Performance((String) ds.child("EventName").getValue());
            
            return event;
        }
    }
    
    private Map<String, String> putEventTypeValues(Event event, Map<String, String> data) {
        if (event instanceof Workshop) {
            data.put("Presenter", ((Workshop) event).getPresenter());
            data.put("MaxUsers", String.valueOf(((Workshop) event).getMaxUsers()));
            data.put("EventType", "Workshop");
        } else if (event instanceof Lecture) {
            data.put("Presenter", ((Lecture) event).getPresenter());
            data.put("EventType", "Lecture");
        } else {
            data.put("EventType", "Performance");
        }
        return data;
    }
    
    private ArrayList<User> attendantsToUsers(DataSnapshot snapshot) {
        ArrayList<User> users = new ArrayList();
        for (DataSnapshot ds : snapshot.getChildren()) {
            String uid = ds.getKey();
            users.add(new User(uid));
        }
        return users;
    }
    
    /**
     * Tells a random object to wait while in a loop.
     * The loop stops, and won't cause any unnecessary cpu use.
     */
    private void lockFXThread() {
        lock = new Object();
        synchronized (lock) {
            while (!done) {
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DBEventModifier.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
        done = false;
    }
    
    /**
     * Wakes the lock. The while loop in the method 'lockFXThread' will proceed and break free.
     */
    private void unlockFXThread() {
        synchronized (lock) {
            done = true;
            lock.notifyAll();
        }
    }
}
