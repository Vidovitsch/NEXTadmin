import Enums.EventType;
import Models.EventDay;
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
public class TestEventDay {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    EventDay instance;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setup">
    @Before
    public void setUp() {
        instance = new EventDay("NextWeek");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestEventDay() {
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
    
    //<editor-fold defaultstate="collapsed" desc="Test Get EventType">
    @Test
    public void testGetEventType() {
        EventType expected = EventType.None;
        EventType actual = instance.getEventType();
        String message = "The expected value '" + expected.name() + "' was not the same as '" + actual.name() + "'";
        assertEquals(message, expected, actual);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Test Get/Set ID">
    @Test
    public void testGetSetID() {
        //Set
        String expected = "1";
        instance.setId(expected);

        //Get
        String actual = instance.getId();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

        assertEquals(message, expected, actual);
    }
    //</editor-fold>
    
    //</editor-fold>
}
