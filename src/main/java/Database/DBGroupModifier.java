/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Models.Group;
import Models.Message;
import Models.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author David
 */
public class DBGroupModifier implements IModGroup {
    
    private Firebase firebase;
    private Object lock;
    private boolean done = false;
    private Group group = null;
    private String uid = "";
    private String groupNumber = "";
    
    public DBGroupModifier() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (Firebase) connector.getConnectionObject();
    }

    @Override
    public void insertGroup(Group group) {
        //Adding a group to firebase
        Map<String, String> data = new HashMap();
        data.put("Name", group.getGroupName());
        data.put("Location", String.valueOf(group.getLocation()));
        Firebase ref = firebase.child("Group").child(String.valueOf(group.getGroupNumber()));
        ref.setValue(data);
        
        //Adding users to the group
        Firebase fb;
        for (User user : group.getUsers()) {
            fb = ref.child("Members").child(user.getUid());
            fb.setValue("NS");
        }
        
        //Adding messages to the group
        Firebase fb2;
        Map<String, String> msgData = new HashMap();
        for (Message message : group.getMessages()) {
            msgData.put("uid", message.getUid());
            msgData.put("group", String.valueOf(message.getGroupNumber()));
            msgData.put("content", message.getContent());
            fb2 = ref.child("Messages").child(String.valueOf(message.getDate()));
            fb2.setValue(msgData);
        }
    }

    @Override
    public void addUser(Group group, User user) {
        Firebase ref = firebase.child("Group").child(String.valueOf(group.getGroupNumber()))
                .child("Members").child(user.getUid());
        ref.setValue("NS");
    }

    @Override
    public void removeUser(Group group, User user) {
        Firebase ref = firebase.child("Group").child(String.valueOf(group.getGroupNumber()))
                .child("Members").child(user.getUid());
        ref.removeValue();
    }
	

    @Override
    public Group getGroup(final String uid) {
        Firebase refUser = firebase.child("User");
        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                groupNumber = String.valueOf(snapshot.child("opzo80Mv3fNZoZNmzj2eJBr3Jfc2").child("GroupID").getValue());
                unlockFXThread();
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println(fe.toException().toString());
            }
        });
        
        
        lockFXThread();
        
        if (!groupNumber.equals(""))
        {
            Firebase refGroup = firebase.child("Group");
            refGroup.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    DataSnapshot ds = snapshot.child(groupNumber);
                    ArrayList<Message> messages = new ArrayList();
                    ArrayList<User> members = new ArrayList();

                    //Fetching other data
                    int groupNumber = Integer.valueOf((String) ds.getKey());
                    int location=  Integer.valueOf(String.valueOf(ds.child("Location").getValue()));
                    String groupName = (String) ds.child("Name").getValue();

                    //Fetching messages
                    Message msg;
                    for (DataSnapshot ds2 : ds.child("Messages").getChildren()) {
                        String date = ds2.getKey();
                        String content = String.valueOf(ds2.child("Content").getValue());
                        String uid = String.valueOf(ds2.child("UID").getValue());
                        msg = new Message(uid, 0, content, date);
                        // TODO: change to get name instead of double uid
                        messages.add(msg);
                    }

                    group = new Group(groupNumber);
                    group.setGroupName(groupName);
                    group.setMessages(messages);
                    group.setUsers(members);
                    group.setLocation(location);
                    unlockFXThread();
                }

                @Override
                public void onCancelled(FirebaseError fe) {
                    System.out.println(fe.toException().toString());
                }
            });
        }
        lockFXThread();
        return group;
    }
    
    @Override
    public String getUid(final String email)
    {
        Firebase ref = firebase.child("User");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Fetching members
                    String emailDB = (String) ds.child("Mail").getValue();
                    if (emailDB.equals(email)) {
                        uid = ds.getKey();
                    }
                }
                unlockFXThread();
            }
            
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println(fe.toException().toString());
            }
        });
        lockFXThread();
        return uid;
    }
    
    @Override
    public List<Message> addNamesToMessages(final List<Message> messages) {
        Firebase ref = firebase.child("User");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (Message msg : messages) 
                {
                    msg.setUserName(String.valueOf(snapshot.child(msg.getUid()).child("Name").getValue()));
                }
                unlockFXThread();
            }
            
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println(fe.toException().toString());
            }
        });
        lockFXThread();
        return messages;
    }

    @Override
    public void addMessage(Message message) {
        Firebase ref = firebase.child("Group").child(String.valueOf(message.getGroupNumber())).
                child("Messages").child(String.valueOf(message.getDate()));
        Map<String, String> msgData = new HashMap();
        msgData.put("uid", message.getUid());
        msgData.put("group", String.valueOf(message.getGroupNumber()));
        msgData.put("content", message.getContent());
        ref.setValue(msgData);
    }
    
    @Override
    public ArrayList<Group> getGroups() {
        final ArrayList<Group> groups = new ArrayList();
        Firebase ref = firebase.child("Group");
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
                    int groupNumber = Integer.valueOf((String) ds.getKey());
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
            public void onCancelled(FirebaseError fe) {
                System.out.println(fe.toException().toString());
            }
        });
        
        lockFXThread();
        return groups;
    }
    
    @Override
    public int getMaxGroupNumber() {
        final Set<Integer> groupNumbers = new TreeSet();
        Firebase ref = firebase.child("Group");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    int groupNumber = Integer.valueOf((String) ds.getKey());
                    groupNumbers.add(groupNumber);
                }
                unlockFXThread();
            }
            
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println(fe.toException().toString());
            }
        });
        List<Integer> numbers = new ArrayList(groupNumbers);
        return numbers.get(numbers.size() - 1);
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
