import Models.StudentManagerViewModel;
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
public class TestStudentManagerViewModel {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    StudentManagerViewModel instance;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new StudentManagerViewModel();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestStudentManagerViewModel() {
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
    
        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Mail Selected">
        @Test
        public void testGetSetMailSelected() {
            //Set
            String expected = "admin@next.nl";
            instance.setMailSelected(expected);

            //Get
            String actual = instance.getMailSelected();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Workshop Selected">
        @Test
        public void testGetSetWorkshopSelected() {
            //Set
            String expected = "My Workshop";
            instance.setWorkshopSelected(expected);

            //Get
            String actual = instance.getWorkshopSelected();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Group Selected">
        @Test
        public void testGetSetGroupSelected() {
            //Set
            String expected = "Group 1";
            instance.setGroupSelected(expected);

            //Get
            String actual = instance.getGroupSelected();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Groups">
        @Test
        public void testGetSetGroups() {
            //Set
            ArrayList<String> expected = new ArrayList<String>();
            String toAdd = "My Group";
            expected.add(toAdd);

            instance.setGroups(expected);

            //Get
            ArrayList<String> actual = instance.getGroups();
            String message = "The expected value '" + expected.toString() + "' was not the same as '" + actual.toString() + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Workshops">
        @Test
        public void testGetSetWorkshops() {
            //Set
            ArrayList<String> expected = new ArrayList<String>();
            String toAdd = "My Workshop";
            expected.add(toAdd);

            instance.setGroups(expected);

            //Get
            ArrayList<String> actual = instance.getGroups();
            String message = "The expected value '" + expected.toString() + "' was not the same as '" + actual.toString() + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>
    
    //</editor-fold>
}
