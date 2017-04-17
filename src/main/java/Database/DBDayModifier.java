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
 *
 * @author Arno Dekkers Los
 */
public class DBDayModifier implements IModDay {
    private static Firebase firebase;
    private boolean done = false;
    private Object lock;

    public DBDayModifier() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (Firebase) connector.getConnectionObject();
    }

    @Override
    public void insertDay(EventDay day) {
        Map<String, String> data = new HashMap();
        data.put("EventName", day.getEventName());
        data.put("StartTime", day.getStartTime());
        data.put("EndTime", day.getEndTime());
        data.put("Date", day.getDate());
        data.put("LocationName", day.getLocationName());
        data.put("Description", day.getDescription());
        
        Firebase ref = firebase.child("Days").push();
        ref.setValue(data);
    }

    @Override
    public void removeDay(EventDay day) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<EventDay> getDays() {
        final ArrayList<EventDay> days = new ArrayList();
        Firebase ref = firebase.child("Days");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String date = (String) ds.child("Date").getValue();
                    String startTime = (String) ds.child("StartTime").getValue();
                    String endTime = (String) ds.child("EndTime").getValue();
                    String locationName = (String) ds.child("LocationName").getValue();
                    String eventName = (String) ds.child("EventName").getValue();
                    String description = (String) ds.child("Description").getValue();
                    
                    EventDay day = new EventDay(eventName);
                    day.setStartTime(startTime);
                    day.setEndTime(endTime);
                    day.setDate(date);
                    day.setLocationName(locationName);
                    day.setDescription(description);
                    
                    days.add(day);
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
