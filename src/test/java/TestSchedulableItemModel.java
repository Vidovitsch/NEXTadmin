import Models.ScheduleableItemModel;
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
public class TestSchedulableItemModel {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    ScheduleableItemModel instance;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new ScheduleableItemModel();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestSchedulableItemModel() {
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

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Event Name">
        @Test
        public void testSetGetEventName() {
            //Set
            String expected = "NextWeek";
            instance.setEventName(expected);

            //Get
            String actual = instance.getEventName();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Start Time">
        @Test
        public void testSetGetStartTime() {
            //Set
            String expected = "14:00";
            instance.setStartTime(expected);

            //Get
            String actual = instance.getStartTime();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set End Time">
        @Test
        public void testSetGetEndTime() {
            //Set
            String expected = "16:00";
            instance.setEndTime(expected);

            //Get
            String actual = instance.getEndTime();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Date">
        @Test
        public void testSetGetDate() {
            //Set
            String expected = "26-06-2017";
            instance.setDate(expected);

            //Get
            String actual = instance.getDate();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Day">
        @Test
        public void testSetGetDay() {
            //Set
            String expected = "Monday";
            instance.setDay(expected);

            //Get
            String actual = instance.getDay();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Location Name">
        @Test
        public void testSetGetLocationName() {
            //Set
            String expected = "Klokgebouw";
            instance.setLocationName(expected);

            //Get
            String actual = instance.getLocationName();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Description">
        @Test
        public void testSetGetDescription() {
            //Set
            String expected = "This is a description";
            instance.setDescription(expected);

            //Get
            String actual = instance.getDescription();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Presenter">
        @Test
        public void testSetGetPresenter() {
            //Set
            String expected = "Steve Jobs";
            instance.setPresenter(expected);

            //Get
            String actual = instance.getPresenter();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Image URL">
        @Test
        public void testSetGetImageURL() {
            //Set
            String expected = "https://image.prntscr.com/image/OoPbgmLcQZSSvOUmkuO8BA.png";
            instance.setImageURL(expected);

            //Get
            String actual = instance.getImageURL();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Max Users">
        @Test
        public void testSetGetMaxUsers() {
            //Set
            String expected = "5";
            instance.setMaxUsers(expected);

            //Get
            String actual = instance.getMaxUsers();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Type">
        @Test
        public void testSetGetType() {
            //Set
            String expected = "Performance";
            instance.setType(expected);

            //Get
            String actual = instance.getType();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
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
        
    //</editor-fold>
}
