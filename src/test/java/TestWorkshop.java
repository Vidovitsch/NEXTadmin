import Enums.EventType;
import Models.User;
import Models.Workshop;
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
public class TestWorkshop {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    Workshop instance;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new Workshop("My Workshop");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestWorkshop() {
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
    
        //<editor-fold defaultstate="collapsed" desc="Test Get Event Type">
        @Test
        public void testGetEventType() {
            //Set
            EventType expected = EventType.Workshop;
            EventType actual = instance.getEventType();

            //Get
            String message = "The expected value '" + expected.name() + "' was not the same as '" + actual.name() + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Presenter">
        @Test
        public void testGetSetPresenter() {
            //Set
            String expected = "Steve Jobs";
            instance.setPresenter(expected);

            //Get
            String actual = instance.getPresenter();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Max Users">
        @Test
        public void testGetSetMaxUsers() {
            //Set
            int expected = 5;
            instance.setMaxUsers(expected);

            //Get
            int actual = instance.getMaxUsers();
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

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Hex Color">
        @Test
        public void testSetGetHexColor() {
            //Set
            String expected = "#0000FF";
            instance.setHexColor(expected);

            //Get
            String actual = instance.getHexColor();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Attending Users">
        @Test
        public void testGetSetAttendingUsers() {
            //Set
            int expected = 5;
            instance.setAttendingUsers(expected);

            //Get
            int actual = instance.getAttendingUsers();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>
    
    //</editor-fold>
}
