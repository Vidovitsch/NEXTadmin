import Models.Lecture;
import Models.Performance;
import Models.Workshop;
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
public class TestEvent {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    Lecture lectureInstance;
    Performance performanceInstance;
    Workshop workshopInstance;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @After
    public void tearDown() {
    }

    public TestEvent() {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setup">
    @Before
    public void setUp() {
        lectureInstance = new Lecture("NextWebLecture");
        performanceInstance = new Performance("NextWebPerformance");
        workshopInstance = new Workshop("NextWebWorkshop");
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Test Constructors">
    @Test
    public void testConstructorLecture() {
        String expected = "NextWebLecture";
        String actual = lectureInstance.getEventName();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

        assertEquals(message, expected, actual);
    }

    @Test
    public void testConstructorWorkshop() {
        String expected = "NextWebWorkshop";
        String actual = workshopInstance.getEventName();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

        assertEquals(message, expected, actual);
    }

    @Test
    public void testConstructorPerformance() {
        String expected = "NextWebPerformance";
        String actual = performanceInstance.getEventName();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

        assertEquals(message, expected, actual);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Test Methods">
    
        //<editor-fold defaultstate="collapsed" desc="Test Get/Set ID">
    
    
    @Test
    public void testSetGetID() {
        //Set
        String expected = "1";
        lectureInstance.setId(expected);

        //Get
        String actual = lectureInstance.getId();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

        assertEquals(message, expected, actual);
    }
    
    
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Hex Color">
    
    
    @Test
    public void testSetGetHexColor() {
        //Set
        String expected = "#0000FF";
        lectureInstance.setHexColor(expected);

        //Get
        String actual = lectureInstance.getHexColor();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

        assertEquals(message, expected, actual);
    }
    
    
    //</editor-fold>
    
        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Image URL">
    
    
    @Test
    public void testSetGetImageURL() {
        //Set
        String expected = "https://image.prntscr.com/image/OoPbgmLcQZSSvOUmkuO8BA.png";
        lectureInstance.setImageURL(expected);

        //Get
        String actual = lectureInstance.getImageURL();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

        assertEquals(message, expected, actual);
    }
    
    
    //</editor-fold>

    //</editor-fold>
    
}