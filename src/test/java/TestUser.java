import Enums.Course;
import Enums.UserRole;
import Enums.UserStatus;
import Models.User;
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
public class TestUser {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    User instance;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new User("0");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestUser() {
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
    
        //<editor-fold defaultstate="collapsed" desc="Test Get/Set UID">
        @Test
        public void testGetSetUID() {
            //Set
            String expected = "2";
            instance.setUid(expected);

            //Get
            String actual = instance.getUid();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Name">
        @Test
        public void testGetSetName() {
            //Set
            String expected = "Stevejobs1996";
            instance.setName(expected);

            //Get
            String actual = instance.getName();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Email">
        @Test
        public void testGetSetEmail() {
            //Set
            String expected = "admin@next.nl";
            instance.setEmail(expected);

            //Get
            String actual = instance.getEmail();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set User Role">
        @Test
        public void testGetSetUserRole() {
            //Set
            UserRole expected = UserRole.Student;
            instance.setUserRole(expected);

            //Get
            UserRole actual = instance.getUserRole();
            String message = "The expected value '" + expected.name() + "' was not the same as '" + actual.name() + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set User Status">
        @Test
        public void testGetSetUserStatus() {
            //Set
            UserStatus expected = UserStatus.Attending;
            instance.setUserStatus(expected);

            //Get
            UserStatus actual = instance.getUserStatus();
            String message = "The expected value '" + expected.name() + "' was not the same as '" + actual.name() + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Course">
        @Test
        public void testGetSetCourse() {
            //Set
            Course expected = Course.Software_Engineering;
            instance.setCourse(expected);

            //Get
            Course actual = instance.getCourse();
            String message = "The expected value '" + expected.name() + "' was not the same as '" + actual.name() + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Semester">
        @Test
        public void testGetSetSemester() {
            //Set
            int expected = 4;
            instance.setSemester(expected);

            //Get
            int actual = instance.getSemester();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Group ID">
        @Test
        public void testGetSetGroupId() {
            //Set
            int expected = 1;
            instance.setGroupID(expected);

            //Get
            int actual = instance.getGroupID();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>
    
    //</editor-fold>
}
