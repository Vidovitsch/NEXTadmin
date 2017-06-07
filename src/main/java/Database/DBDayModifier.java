/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Models.EventDay;
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
 * This Class is used to comunicate between the application and the firebase,
 * specificly the child "Days" from the firebase implements IModDay
 * @author Arno Dekkers Los
 */
public class DBDayModifier implements IModDay {
    private static Firebase firebase;
    private boolean done = false;
    private Object lock;

    /**
     * The constructor of the DBDayModifier class, The method takes no arguments.
     * It initiates the field firebase by creating a connection using the FBConnector class
     */
    public DBDayModifier() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (Firebase) connector.getConnectionObject();
    }

    /**
     * This method is used to to add a new day to the Firebase database.
     * As parameters it takes the EventDay object that it has to add, it uses the putDayValues method
     * to add the values of the EventDay to a Map<String, String> which it can push to the Firebase database
     * @param day not null
     */
    @Override
    public void insertDay(EventDay day) {
        if(day == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " tried to add EventDay null to firebase");
        }
        Map<String, String> data = new HashMap();
        putDayValues(data, day);
        Firebase ref = firebase.child("Days").push();
        ref.setValue(data);
    }
    
    /**
     * This method is used to update an existing EventDay in the database, as argument it takes an EventDay instance 
     * which contains the updated values. it uses the putDayValues method to put the EventDay values to a Map<String, String>
     * After that it pushes the new data to the firebase at the branch days where the ID matches the id of the object
     * @param day not null
     */
    public void updateDay(EventDay day){
        if(day == null || "".equals(day.getId())){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " tried to call updateDay method with an null instance as day");
        }
        Map<String, String> data = new HashMap();
        putDayValues(data, day);
        Firebase ref = firebase.child("Days").child(day.getId());
        ref.setValue(data);
    }

    /**
     * This method is used to put the differnt values from a EventDay instance to a Map<String, String>
     * this is important because with the new format we can push the data to the firebase
     * The method is used by insertDay and updateDay. as arguments it takes a Map<String, String> which is
     * the object the data has to be added too. and a EventDay which contains the values that have to be added
     * @param data not null
     * @param day not null
     */
    private void putDayValues(Map<String, String> data, EventDay day){
        if(data == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the data instance was null");
        }else if(day == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the day instance was null");
        }
        data.put("EventName", day.getEventName());
        data.put("StartTime", day.getStartTime());
        data.put("EndTime", day.getEndTime());
        data.put("Date", day.getDate());
        data.put("LocationName", day.getLocationName());
        data.put("Description", day.getDescription());
    }
    
    /**
     * this method is used to remove an existing day from the firebase. As argument it takes an EventDay
     * which speciefies what event has to be deleted. An IllegalArgumentException will be thrown if the ID equals null
     * @param day not null
     */
    @Override
    public void removeDay(EventDay day) {
        if(day == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the day instance was null");
        }else if(day.getId() == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the day instance did not contain an ID");
        }
        Firebase ref = firebase.child("Days").child(day.getId());
        ref.removeValue();
    }

    /**
     * This method is used to fetch all the existing childs from the day branch from the firebase.
     * the data is loaded in an ArrayList<EventDay> days and returned. to stop the FXThread until the 
     * method is finished loading loading the method uses the lockFXThread and unlockFXThread methods
     * @return days
     */
    @Override
    public ArrayList<EventDay> getDays() {
        final ArrayList<EventDay> days = new ArrayList();
        Firebase ref = firebase.child("Days");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    days.add(dsToEventDay(ds));
                }
                unlockFXThread();
            }
            
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println(fe.toException().toString());
            }
        });
        
        lockFXThread();
        return days;
    }
    
    /**
     * This method is used to fetch a specific day from the firebase, as argument it takes an ID
     * this id is the child of days that has to be fetched from the firebase
     * to convert the data from the database to a EventDay instance it uses the method dsToEventDay
     * @param id not null, ""
     * @return days(0)
     */
    public EventDay getDay(final String id) {
        if(id == null || "".equals(id)){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the ID of the object that has to be retrieved is null");
        }
        final ArrayList<EventDay> days = new ArrayList();
        Firebase ref = firebase.child("Days/" + id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                EventDay day = dsToEventDay(snapshot);
                day.setId(id);
                days.add(day);
                unlockFXThread();
            }
            
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println(fe.toException().toString());
            }
        });
        
        lockFXThread();
        return days.get(0);
    }
    
    /**
     * this method is used to convert a DataSnapshot to a EventDay instance
     * @param ds not null
     * @return day
     */
    private EventDay dsToEventDay(DataSnapshot ds){
        if(ds == null){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the ds that had to be converted equaled null");
        }
        String date = (String) ds.child("Date").getValue();
        String startTime = (String) ds.child("StartTime").getValue();
        String endTime = (String) ds.child("EndTime").getValue();
        String locationName = (String) ds.child("LocationName").getValue();
        String eventName = (String) ds.child("EventName").getValue();
        String description = (String) ds.child("Description").getValue();
        String id = (String) ds.getKey();

        EventDay day = new EventDay(eventName);
        day.setStartTime(startTime);
        day.setEndTime(endTime);
        day.setDate(date);
        day.setLocationName(locationName);
        day.setDescription(description);
        day.setId(id);
        return day;
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
