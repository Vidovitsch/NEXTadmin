package Database;

import com.firebase.client.Firebase;


/**
 * This method is used to make a connection with the firebase database
 * it is used by all the different DB***Modifier classes
 * it implements the IDatabase interface
 * @author David
 */
public class FBConnector implements IDatabase {

    private static FBConnector instance = null;
    private String firebase_url = null;
    private Firebase firebase;
    
    /**
     * This method is used to get the instance this class in case that the instance
     * is still null a new instance of FBConnector get's created
     * @return instance
     */
    public static FBConnector getInstance() {
        if (instance == null) {
            instance = new FBConnector();
        }
        return instance;
    }

    /**
     * private constructor for this singleton can only be called from getInstance
     */
    private FBConnector() {
        this.firebase = null;
    }
    
    /**
     * This methods connects the firebase field to the firebase
     * It has a check whether or not the connection has been made yet
     */
    @Override
    public void connect() {
        if (firebase_url == null) {
            firebase_url = "https://nextweek-b9a58.firebaseio.com/";
            firebase = new Firebase(firebase_url);
        }
    }

    /**
     * this method is used to check wheather or not the firebase field has been initiated yet
     * @return firebase
     * @throws NullPointerException if firebase equals null 
     */
    @Override
    public Object getConnectionObject() throws NullPointerException {
        if (firebase != null) {
            return firebase;
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
        return firebase != null;
    }
}
