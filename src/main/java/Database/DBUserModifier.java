/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Enums.Course;
import Enums.UserRole;
import Enums.UserStatus;
import Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arno Dekkers Los
 */
public class DBUserModifier implements IModUsers{
    private static DatabaseReference firebase;
    private boolean done = false;
    private Object lock;

    /**
     * The constructor of the DBUserModifier class, The method takes no arguments.
     * It initiates the field firebase by creating a connection using the FBConnector class
     */
    public DBUserModifier() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (DatabaseReference) connector.getConnectionObject();
    }
    
    /**
     * This method retrieves a list of users that are students and are not 
     * assigned a group yet.
     * 
     * @return retV
     */
    @Override
    public ArrayList<User> getUnassignedUsers() {
        final ArrayList<User> retV = new ArrayList<User>();
        DatabaseReference ref = firebase.child("User");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user;
                String email = null;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.hasChild("Mail")){
                        email = (String)ds.child("Mail").getValue();
                    }
                    String groupID = "-1";
                    if(ds.hasChild("GroupID")){
                        groupID = (String)ds.child("GroupID").getValue();
                    }
                    if(email.contains("@student.fontys.nl") && "-1".equals(groupID)){
                        UserRole userRole = UserRole.valueOf((String) ds.child("Role").getValue());
                        String name = (String) ds.child("Name").getValue();
                        UserStatus userStatus = UserStatus.valueOf((String) ds.child("Status").getValue());
                        char courseChar = ((String)ds.child("Course").getValue()).charAt(0);
                        Course course = Course.getCourse(courseChar);
                        int semester = Integer.valueOf((String) ds.child("Semester").getValue());
                        user = new User(ds.getKey());
                        user.setName(name);
                        user.setEmail(email);
                        user.setUserRole(userRole);
                        user.setUserStatus(userStatus);
                        user.setCourse(course);
                        user.setSemester(semester);
                        retV.add(user);
                    }
                }
                unlockFXThread();
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        lockFXThread();
        return retV;
    }
    
    /**
     * Tells a random object to wait while in a loop. The loop stops, and won't
     * cause any unnecessary cpu use.
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
     * Wakes the lock. The while loop in the method 'lockFXThread' will proceed
     * and break free.
     */
    private void unlockFXThread() {
        synchronized (lock) {
            done = true;
            lock.notifyAll();
        }
    }
}
