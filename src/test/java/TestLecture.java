import Enums.EventType;
import Models.Lecture;
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

public class TestLecture {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    Lecture instance;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new Lecture("NextWeek");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    @BeforeClass
    public static void setUpClass() {
    }

    public TestLecture() {
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
            //Get
            EventType expected = EventType.Lecture;
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
    
    //</editor-fold>
}
