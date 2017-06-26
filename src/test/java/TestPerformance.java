import Enums.EventType;
import Models.Performance;
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
public class TestPerformance {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    Performance instance;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new Performance("NextWeek");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestPerformance() {
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
            //Get
            EventType expected = EventType.Performance;
            EventType actual = instance.getEventType();

            //Asserts
            String message = "The expected value '" + expected.name() + "' was not the same as '" + actual.name() + "'";
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
    
    //</editor-fold>
}
