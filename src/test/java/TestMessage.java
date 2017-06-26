import Models.Message;
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
public class TestMessage {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    Message instanceWithName;
    Message instanceNoName;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instanceWithName = new Message("1", "SteveJobs1996", 0, "Hey guys, I just invented a cool new phone!", "26-06-2017");
        instanceNoName = new Message("2", 0, "This is an anonymous message", "26-06-2017");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestMessage() {
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

            //<editor-fold defaultstate="collapsed" desc="Test Constructor With Username">
            @Test
            public void testConstructorWithName() {
                //Expected values
                String expectedUID = "1";
                String expectedUsername = "SteveJobs1996";
                int expectedGroupNumber = 0;
                String expectedContent = "Hey guys, I just invented a cool new phone!";
                String expectedDate = "26-06-2017";

                //Actual values
                String actualUID = instanceWithName.getUid();
                String actualUsername = instanceWithName.getUserName();
                int actualGroupNumber = instanceWithName.getGroupNumber();
                String actualContent = instanceWithName.getContent();
                String actualDate = instanceWithName.getDate();

                //Messages
                String uidMessage = "The expected value '" + expectedUID + "' was not the same as '" + actualUID + "'";
                String usernameMessage = "The expected value '" + expectedUsername + "' was not the same as '" + actualUsername + "'";
                String groupNumberMessage = "The expected value '" + expectedGroupNumber + "' was not the same as '" + actualGroupNumber + "'";
                String contentMessage = "The expected value '" + expectedContent + "' was not the same as '" + actualContent + "'";
                String dateMessage = "The expected value '" + expectedDate + "' was not the same as '" + actualDate + "'";

                //Asserts
                assertEquals(uidMessage, expectedUID, actualUID);
                assertEquals(usernameMessage, expectedUsername, actualUsername);
                assertEquals(groupNumberMessage, expectedGroupNumber, actualGroupNumber);
                assertEquals(contentMessage, expectedContent, actualContent);
                assertEquals(dateMessage, expectedDate, actualDate);
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Test Constructor Without Username">
            @Test
            public void testConstructorNoName() {
                //Expected values
                String expectedUID = "2";
                int expectedGroupNumber = 0;
                String expectedContent = "This is an anonymous message";
                String expectedDate = "26-06-2017";

                //Actual values
                String actualUID = instanceNoName.getUid();
                int actualGroupNumber = instanceNoName.getGroupNumber();
                String actualContent = instanceNoName.getContent();
                String actualDate = instanceNoName.getDate();

                //Messages
                String uidMessage = "The expected value '" + expectedUID + "' was not the same as '" + actualUID + "'";
                String groupNumberMessage = "The expected value '" + expectedGroupNumber + "' was not the same as '" + actualGroupNumber + "'";
                String contentMessage = "The expected value '" + expectedContent + "' was not the same as '" + actualContent + "'";
                String dateMessage = "The expected value '" + expectedDate + "' was not the same as '" + actualDate + "'";

                //Asserts
                assertEquals(uidMessage, expectedUID, actualUID);
                assertEquals(groupNumberMessage, expectedGroupNumber, actualGroupNumber);
                assertEquals(contentMessage, expectedContent, actualContent);
                assertEquals(dateMessage, expectedDate, actualDate);
            }
            //</editor-fold>

        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Group Number">
        @Test
        public void testGetSetGroupNumber() {
            //Set
            int expected = 1;
            instanceWithName.setGroupNumber(expected);

            //Get
            int actual = instanceWithName.getGroupNumber();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set UID">
        @Test
        public void testGetSetUID() {
            //Set
            String expected = "5";
            instanceWithName.setUid(expected);

            //Get
            String actual = instanceWithName.getUid();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Content">
        @Test
        public void testGetSetContent() {
            //Set
            String expected = "This is a test message";
            instanceWithName.setContent(expected);

            //Get
            String actual = instanceWithName.getContent();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Date">
        @Test
        public void testGetSetDate() {
            //Set
            String expected = "26-06-2017";
            instanceWithName.setDate(expected);

            //Get
            String actual = instanceWithName.getDate();

            //Asserts
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";
            assertEquals(message, expected, actual);

            //testing exception for an incorrect format
            try {
                String expectedFalse = "not-a-time";
                instanceWithName.setDate(expectedFalse);
                fail();
            } catch (IllegalArgumentException e) {
                String actualMessage = e.getMessage();
                String expectedMessage = "the date string had an invallid format. format should be dd-MM-yyyy";
                String exceptionMessage = "The expected exception '" + expectedMessage + "' was not the same as '" + actualMessage + "'";
                assertEquals(exceptionMessage, expectedMessage, actualMessage);

                //new actual end time
                actual = instanceWithName.getDate();
                assertEquals(message, expected, actual);
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Username">
        @Test
        public void testGetSetUsername() {
            //Set
            String expected = "This is a test message";
            instanceWithName.setUserName(expected);

            //Get
            String actual = instanceWithName.getUserName();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>
    
    //</editor-fold>
}
