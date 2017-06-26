
import Models.ScheduledItemModel;
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
public class TestScheduleItemModel {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    ScheduledItemModel instance;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new ScheduledItemModel("2", "MyString");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestScheduleItemModel() {
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
    
        //<editor-fold defaultstate="collapsed" desc="Test Constructor">
        @Test
        public void testConstructor() {
            String expectedID = "2";
            String expectedString = "MyString";

            String actualID = instance.getId();
            String actualString = instance.getString();

            String idMessage = "The expected value '" + expectedID + "' was not the same as '" + actualID + "'";
            String stringMessage = "The expected value '" + expectedString + "' was not the same as '" + actualString + "'";

            assertEquals(idMessage, expectedID, actualID);
            assertEquals(stringMessage, expectedString, actualString);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Add Special Chars">
        @Test
        public void testAddSpecialChars() {
            String original = "Result";
            String expected = "'Result'";

            String actual = instance.AddSpecialChars(original);
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set ID">
        @Test
        public void testSetGetID() {
            //Set
            String expected = "1";
            instance.setId(expected);

            //Get
            String actual = instance.getId();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set String">
        @Test
        public void testSetGetString() {
            //Set
            String expected = "String";
            instance.setString(expected);

            //Get
            String actual = instance.getString();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>
    
    //</editor-fold>
}
