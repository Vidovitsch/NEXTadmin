import Models.QRViewModel;
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
public class TestQRViewModel {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    QRViewModel instance;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Up">
    @Before
    public void setUp() {
        instance = new QRViewModel();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Not Used">
    public TestQRViewModel() {
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
    
        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Generated">
        @Test
        public void testSetGetGenerated() {
            //Set
            String expected = "2134123141324124";
            instance.setGenerated(expected);

            //Get
            String actual = instance.getGenerated();
            String message = "The expected value '" + expected + "' was not the same as '" + actual + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Test Get/Set Codes">
        @Test
        public void testGetSetCodes() {
            //Set
            ArrayList<String> expected = new ArrayList<String>();
            String toAdd = "dasdsadsad";
            expected.add(toAdd);

            instance.setCodes(expected);

            //Get
            ArrayList<String> actual = instance.getCodes();
            String message = "The expected value '" + expected.toString() + "' was not the same as '" + actual.toString() + "'";

            assertEquals(message, expected, actual);
        }
        //</editor-fold>
    
    //</editor-fold>

}
