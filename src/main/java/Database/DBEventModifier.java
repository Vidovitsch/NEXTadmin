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
 * This class is used to comunicate with the firebase,
 * specifically the child Events implements IModEvent
 * @author David
 */
public class DBEventModifier implements IModEvent {
    
    private static Firebase firebase;
    private Object lock;
    private boolean done = false;

    /**
     * The constructor of the DBEventModifier class, The method takes no arguments.
     * It initiates the field firebase by creating a connection using the FBConnector class
     */
    public DBEventModifier() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (Firebase) connector.getConnectionObject();
    }

    /**
     * This method is used to update the database to add a existing user to an existing event
     * The method takes the parameters event and user, if either of the id's fields is emtpy an exception is thrown
     * @param event not null
     * @param user not null
     */
    @Override
    public void addAttendingUser(Workshop event, User user) {
        if(event == null || "".equals(event.getId())){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the event instance's id was null");
        }else if(user == null || "".equals(user.getUid())){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the users instance's UID was null");
        }
        Firebase ref = firebase.child("Event").child(event.getId()).child("Attending").child(user.getUid());
        ref.setValue("Attending");
    }
    
    /**
     * This method is used to remove an attending user from a workshop, it takes the argumetns
     * event and user which specify which event and which user. the id's in these instances
     * are not able to null or an exception will be thrown
     * @param event not null
     * @param user not null
     */
    @Override
    public void removeAttendingUser(Workshop event, User user) {
        if(event == null || "".equals(event.getId())){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the event instance's id was null");
        }else if(user == null || "".equals(user.getUid())){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the user instance's UID was null");
        }
        Firebase ref = firebase.child("Event").child(event.getId()).child("Attending").child(user.getUid());
        ref.removeValue();
    }
    
    /**
     * This method is used to to add a new event to the Firebase database.
     * As parameters it takes the Event object that it has to add, it uses the putEventValues method
     * to add the values of the Event to a Map<String, String> which it can push to the Firebase database
     * @param event not null
     */
    @Override
    public void insertEvent(Event event) {
        if(event == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the event instance's id was null");
        }
        Map<String, String> data = new HashMap();
        putEventValues(event, data);
        Firebase ref = firebase.child("Event").push();
        ref.setValue(data);
    }
    
    /**
     * This method is used to update an existing Event in the database, as argument it takes an Event instance 
     * which contains the updated values. it uses the putEventValues method to put the Event values to a Map<String, String>
     * After that it pushes the new data to the firebase at the branch events where the ID matches the id of the object
     * @param event not null
     */
    public void updateEvent(Event event) {
        if(event == null || "".equals(event.getId())){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the event instance's ID was null");
        }
        Map<String, String> data = new HashMap();
        putEventValues(event, data);
        Firebase ref = firebase.child("Event/" + event.getId());
        ref.setValue(data);
    }
    
    /**
     * this method is used to remove an existing day from the firebase. As argument it takes an EventDay
     * which speciefies what event has to be deleted. An IllegalArgumentException will be thrown if the ID equals null
     * @param event not null
     */
    @Override
    public void removeEvent(Event event) {
        if(event == null || "".equals(event.getId())){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the event instance's ID was null");
        }
        Firebase ref = firebase.child("Event").child(event.getId());
        ref.removeValue();
    }

    /**
     * This method is used to fetch all the existing childs from the event branch from the firebase.
     * the data is loaded in an ArrayList<Event> events and returned with the method dsToEvent. 
     * to stop the FXThread until the method is finished loading loading the method uses 
     * the lockFXThread and unlockFXThread methods
     * @return days
     */
    @Override
    public ArrayList<Event> getEvents() {
        final ArrayList<Event> events = new ArrayList();
        Firebase ref = firebase.child("Event");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    events.add(dsToEvent(ds));
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
    
    /**
     * This method is used to fetch a specific event from the firebase, as argument it takes an ID
     * this id is the child of events that has to be fetched from the firebase
     * to convert the data from the database to a EventDay instance it uses the method dsToEventDay
     * @param id not null, ""
     * @return events(0)
     */
    public Event getEvent(String id) {
        if(id == null || "".equals(id)){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the ID of the object that has to be retrieved is null");
        }
        final ArrayList<Event> events = new ArrayList();
        Firebase ref = firebase.child("Event/" + id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                events.add(dsToEvent(snapshot));
                unlockFXThread();
            }
            
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println(fe.toException().toString());
            }
        });
        
        lockFXThread();
        return events.get(0);
    }
    
    /**
     * this method is used to convert a DataSnapshot to a EventDay instance
     * Because event has mutltiple childs the specifyEvent method is used to load the childs
     * correcly
     * @param ds not null
     * @return day
     */
    private Event dsToEvent(DataSnapshot ds){
        if(ds == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the ds that had to be converted equaled null");
        }
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
        return event;
    }
    
    /**
     * this method is used to correcly load all data from a possible child. It uses
     * the child EventType from the events branch in the firebase to identify the child
     * then it loads the child specific fields
     * @param ds
     * @return event
     */
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
    
    /**
     * This method is used to put the differnt values from a Event instance to a Map<String, String>
     * this is important because with the new format we can push the data to the firebase
     * The method is used by insertEvent and updateEvent. as arguments it takes a Map<String, String> which is
     * the object the data has to be added too. and a Event which contains the values that have to be added
     * because an Event object can be any of the child it uses the putEventTypeValues method to put the specific
     * child values to the Map<String, String>
     * @param data not null
     * @param day not null
     */
    private void putEventValues(Event event, Map<String, String> data) {
        if(data == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the data instance was null");
        }else if(event == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the event instance was null");
        }
        data.put("EventName", event.getEventName());
        data.put("StartTime", event.getStartTime());
        data.put("EndTime", event.getEndTime());
        data.put("Date", event.getDate());
        data.put("ImageURL", event.getImageURL());
        data.put("LocationName", event.getLocationName());
        data.put("Description", event.getDescription());
        data = putEventTypeValues(event, data);
        if(event.getEventType() == EventType.Workshop){
            data.put("maxUsers", String.valueOf(((Workshop)event).getMaxUsers()));
        }
    }
    
    /**
     * this method is used to add the child specific fields to the Map<String, String>
     * this is used by the putEventValues method
     * @param event
     * @param data
     * @return 
     */
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
    
    /**
     * this method is used to convert a DataSnapshot to a list of users
     * @param snapshot not null
     * @return users
     */
    private ArrayList<User> attendantsToUsers(DataSnapshot snapshot) {
        if(snapshot == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the snapshot instance was null");
        }
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
