/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Models.Group;
import Models.Message;
import Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This method is used as a connection between the application and the firebase
 * specifically the Group branch
 * @author David
 */
public class DBGroupModifier implements IModGroup {
    
    private DatabaseReference firebase;
    private Object lock;
    private boolean done = false;
    private Group group = null;
    private String uid = "";
    private String groupNumber = "";
    
    /**
     * The constructor of the DBGroupModifier class, The method takes no arguments.
     * It initiates the field firebase by creating a connection using the FBConnector class
     */
    public DBGroupModifier() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (DatabaseReference) connector.getConnectionObject();
    }

    /**
     * This method is used to update the database to add a existing user to an existing group
     * The method takes the parameters group and user, if either of the id's fields is emtpy an exception is thrown
     * @param group not null
     * @param user not null
     */
    @Override
    public void addUser(Group group, User user) {
        if(group == null || "".equals(group.getGroupNumber())){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the group instance's number was null");
        }else if(user == null || "".equals(user.getUid())){
            throw new IllegalArgumentException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                    " the users instance's UID was null");
        }
        DatabaseReference ref = firebase.child("Group").child(String.valueOf(group.getGroupNumber()))
                .child("Members").child(user.getUid());
        ref.setValue("NS");
    }
    
    /**
     * This method is used to fetch all the existing childs from the group branch from the firebase.
     * the data is loaded in an ArrayList<Group> events. 
     * to stop the FXThread until the method is finished loading loading the method uses 
     * the lockFXThread and unlockFXThread methods
     * @return groups
     */
    @Override
    public ArrayList<Group> getGroups() {
        final ArrayList<Group> groups = new ArrayList();
        DatabaseReference ref = firebase.child("Group");
        
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Group g = null;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ArrayList<Message> messages = new ArrayList();
                    ArrayList<User> members = new ArrayList();
                    
                    //Fetching members
                    User u;
                    for (DataSnapshot ds3 : ds.child("Members").getChildren()) {
                        String uid = ds3.getKey();
                        u = new User(uid);
                        members.add(u);
                    }
                    
                    //Fetching other data
                    String groupNumber = (String) ds.getKey();
                    String groupName = (String) ds.child("Name").getValue();

                    //Fetching messages
                    Message msg;
                    for (DataSnapshot ds2 : ds.child("Messages").getChildren()) {
                        String date = ds2.getKey();
                        String content = (String) ds2.child("content").getValue();
                        int group = Integer.valueOf((String) ds2.child("group").getValue());
                        String uid = (String) ds2.child("UID").getValue();
                        // TODO: change to get name instead of double uid
                        msg = new Message(uid, uid, group, content, date);
                        messages.add(msg);
                    }

                    g = new Group(groupNumber);
                    g.setGroupName(groupName);
                    g.setMessages(messages);
                    g.setUsers(members);
                    
                    groups.add(g);
                }
                unlockFXThread();
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException(getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + 
                        " " + de.getMessage()); 
            }
        });
        
        lockFXThread();
        return groups;
    }
    
    /**
     * Removes all groups from the firebase and resets the group field
     * for all the users
     */
    @Override
    public void resetGroups(){
        DatabaseReference ref = firebase.child("Group");
        ref.removeValue();
        ref = firebase.child("newGroupID");
        ref.setValue("0");
        ref = firebase.child("User");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                DatabaseReference ref2;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String email = (String)ds.child("Mail").getValue();
                    if(email.toLowerCase().contains("@student.fontys.nl")){
                        if(ds.hasChild("GroupID")){
                            ref2 = firebase.child("User/" + (String)ds.getKey() + "/GroupID");
                            ref2.removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    /**
     * This method looks at the group branch in the firebase and selects the highest ID that occurs
     * @return highest ID in firebase + 1
     */
    @Override
    public int getHighestID() {
        final List<Integer> retV = new ArrayList<Integer>();
        retV.add(0, -1);
        DatabaseReference ref = firebase.child("Group");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    int key = Integer.parseInt(ds.getKey());
                    if(key > retV.get(0)){
                        retV.add(0, key);
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
        return retV.get(0) + 1;
    }
    
    /**
     * This method is used to add new groups with their users to the Firebase.
     * This method is used by the UserAllocation algoritm, the reason why this is uses
     * then lose methods to add the group and to add the user is for efficiency
     * the DatabaseRefferences only get created once and are then used to push all the new
     * data rather then creating a new DatabaseRefference for each user/group
     * @param newGroups 
     */
    @Override
    public void addNewGroupsWithUsers(ArrayList<Group> newGroups){
        firebase.keepSynced(true);
        DatabaseReference refGroup;
        DatabaseReference refUser;
        for(Group g : newGroups){
            firebase.child("Group").child(g.getGroupNumber()).child("Name").setValue(g.getGroupName());
            firebase.child("Group").child(g.getGroupNumber()).child("Location").setValue(0);
            for(User u : g.getUsers()){
                firebase.child("Group").child(g.getGroupNumber()).child("Members").child(u.getUid()).setValue("NS");
                firebase.child("User").child(u.getUid()).child("GroupID").setValue(g.getGroupNumber());
            }
        }
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
