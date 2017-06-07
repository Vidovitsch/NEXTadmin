/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author David
 */
public class FBConnector implements IDatabase {

    private static FBConnector instance = null;
    private String firebase_url = null;
    private DatabaseReference databaseReference;

    public static FBConnector getInstance() {
        System.out.println("in FBConnector getinstance");
        if (instance == null) {
            instance = new FBConnector();
        }
        return instance;
    }

    public FBConnector() {
        this.databaseReference = null;
    }

    @Override
    public void connect() {
        System.out.println("trying to connnect in FBConnector");
        if (databaseReference == null) {
            FileInputStream serviceAccount = null;
            try {
                serviceAccount = new FileInputStream("src/main/java/Database/NextWeekDev-f084f8ebd419.json");
                System.out.println("setting FirebaseOptions");
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                        .setDatabaseUrl("https://nextweekdev.firebaseio.com/")
                        .build();
                System.out.println("initializing FirebaseApp");
                FirebaseApp.initializeApp(options);
                System.out.println("Getting FirebaseDatabaseInstance");
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                System.out.println("Getting FirebaseDatabaseReference");
                databaseReference = firebaseDatabase.getReference();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(FBConnector.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(FBConnector.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    serviceAccount.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(FBConnector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("DatabaseReference Exists");
        }
    }

    @Override
    public Object getConnectionObject() throws NullPointerException {
        if (databaseReference != null) {
            return databaseReference;
            //return firebase;
        } else {
            throw new NullPointerException("There is no connection with the database");
        }
    }

    @Override
    public boolean checkConnection() {
        //Is changeable
        return databaseReference != null;
    }
}
