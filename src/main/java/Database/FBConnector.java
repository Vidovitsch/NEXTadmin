/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

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

    /**
     * This method is used to get the instance this class in case that the instance
     * is still null a new instance of FBConnector get's created
     * @return instance
     */
    public static FBConnector getInstance() {
        System.out.println("in FBConnector getinstance");
        if (instance == null) {
            instance = new FBConnector();
        }
        return instance;
    }

    /**
     * private constructor for this singleton can only be called from getInstance
     */
    public FBConnector() {
        this.databaseReference = null;
    }

    /**
     * This methods connects the firebase field to the firebase
     * It has a check whether or not the connection has been made yet
     */
    @Override
    public void connect() {
        System.out.println("trying to connnect in FBConnector");
        if (databaseReference == null) {
            FileInputStream serviceAccount = null;
            try {
                serviceAccount = new FileInputStream("src/main/java/Database/NextWeek-e50906a6dd28.json");
                System.out.println("setting FirebaseOptions");
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                        .setDatabaseUrl("https://nextweek-b9a58.firebaseio.com/")
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

    /**
     * this method is used to check wheather or not the firebase field has been initiated yet
     * @return firebase
     * @throws NullPointerException if firebase equals null 
     */
    @Override
    public Object getConnectionObject() throws NullPointerException {
        if (databaseReference != null) {
            return databaseReference;
            //return firebase;
        } else {
            throw new NullPointerException("There is no connection with the database");
        }
    }

    /**
     * this method returns a boolean whether or not the firebase instance doesn't equal null
     * @return 
     */
    @Override
    public boolean checkConnection() {
        //Is changeable
        return databaseReference != null;
    }
}
