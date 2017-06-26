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
public class TestEventDate {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    Lecture instance;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new Lecture("NextWebLecture");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestEventDate() {
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
    
        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Location Name">
    @Test
    public void testGetSetLocationName() {
        //Set
        String expected = "Klokgebouw";
        instance.setLocationName(expected);

        //Get
        String actual = instance.getLocationName();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

        assertEquals(message, expected, actual);
    }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Date/Day">
    @Test
    public void testGetSetDateDay() {
        //Set
        String expected = "26-06-2017";
        String expectedDay = "Monday";
        instance.setDate(expected);

        //Get
        String actual = instance.getDate();
        String actualDay = instance.getDay();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";
        String dayMessage = "The expected value '" + expectedDay + "' was not the same as '" + actualDay + "'";

        //Asserts
        assertEquals(message, expected, actual);
        assertEquals(dayMessage, expectedDay, actualDay);

        //testing exception for an incorrect format
        try {
            String expectedFalse = "not-a-date";
            instance.setDate(expectedFalse);
            fail();
        } catch (IllegalArgumentException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "the date string had an invallid format. format should be dd-MM-yyyy";
            String exceptionMessage = "The expected exception '" + expectedMessage + "' was not the same as '" + actualMessage + "'";
            assertEquals(exceptionMessage, expectedMessage, actualMessage);

            //new actual date
            actual = instance.getDate();
            assertEquals(message, expected, actual);

            //new actual day
            actualDay = instance.getDay();
            assertEquals(dayMessage, expectedDay, actualDay);
        }
    }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set End Time">
    @Test
    public void testGetSetEndTime() {
        //Set
        String expected = "14:00";
        instance.setEndTime(expected);

        //Get
        String actual = instance.getEndTime();

        //Asserts
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";
        assertEquals(message, expected, actual);

        //testing exception for an incorrect format
        try {
            String expectedFalse = "not-a-time";
            instance.setEndTime(expectedFalse);
            fail();
        } catch (IllegalArgumentException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "The given time was not of a valid format, format should be HH:mm";
            String exceptionMessage = "The expected exception '" + expectedMessage + "' was not the same as '" + actualMessage + "'";
            assertEquals(exceptionMessage, expectedMessage, actualMessage);

            //new actual end time
            actual = instance.getEndTime();
            assertEquals(message, expected, actual);
        }
    }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Start Time">
    @Test
    public void testGetSetStartTime() {
        //Set
        String expected = "14:00";
        instance.setStartTime(expected);

        //Get
        String actual = instance.getStartTime();

        //Asserts
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";
        assertEquals(message, expected, actual);

        //testing exception for an incorrect format
        try {
            String expectedFalse = "not-a-time";
            instance.setStartTime(expectedFalse);
            fail();
        } catch (IllegalArgumentException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "The given time was not of a valid format, format should be HH:mm";
            String exceptionMessage = "The expected exception '" + expectedMessage + "' was not the same as '" + actualMessage + "'";
            assertEquals(exceptionMessage, expectedMessage, actualMessage);

            //new actual start time
            actual = instance.getStartTime();
            assertEquals(message, expected, actual);
        }
    }
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Event Name">
    @Test
    public void testGetSetEventName() {
        //Set
        String expected = "NewEventName";
        instance.setEventName(expected);

        //Get
        String actual = instance.getEventName();
        String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

        assertEquals(message, expected, actual);
    }
        //</editor-fold>
    
    //</editor-fold>
    
}