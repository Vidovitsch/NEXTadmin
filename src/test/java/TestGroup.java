import Models.Group;
import Models.Message;
import Models.User;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Youri van der Ceelen
 */
public class TestGroup {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    Group instance;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new Group("0");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestGroup() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @After
    public void tearDown() {
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Test Methods">
    
        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Group Number">
        @Test
        public void testGetSetGroupNumber() {
            //Set
            String expected = "1";
            instance.setGroupNumber(expected);

            //Get
            String actual = instance.getGroupNumber();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Group Name">
        @Test
        public void testGetSetGroupName() {
            //Set
            String expected = "MyGroup";
            instance.setGroupName(expected);

            //Get
            String actual = instance.getGroupName();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Users">
        @Test
        public void testGetSetUsers() {
            //Set
            ArrayList<User> expected = new ArrayList<User>();
            User toAdd = new User("1");
            expected.add(toAdd);

            instance.setUsers(expected);

            //Get
            ArrayList<User> actual = instance.getUsers();
            String message = "The expected value '" + expected.toString() + "' was not the same as '" + actual.toString() + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Messages">
        @Test
        public void testGetSetMessages() {
            //Set
            ArrayList<Message> expected = new ArrayList<Message>();
            Message toAdd = new Message("1", "MyUser", 1, "This is a message", "26-6-2017");
            expected.add(toAdd);

            instance.setMessages(expected);

            //Get
            ArrayList<Message> actual = instance.getMessages();
            String message = "The expected value '" + expected.toString() + "' was not the same as '" + actual.toString() + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Location">
        @Test
        public void testGetSetLocation() {
            //Set
            int expected = 1;
            instance.setLocation(1);

            //Get
            int actual = instance.getLocation();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

    //</editor-fold>
}
