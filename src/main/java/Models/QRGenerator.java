package Models;

import Database.FBConnector;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import static java.lang.Math.random;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author David
 */
public class QRGenerator {
    private static Firebase firebase;
        
    public QRGenerator() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (Firebase) connector.getConnectionObject();
    }
    
    public void generate() {
        Firebase ref = firebase.child("GroupLocation");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                generateQRCodes((int) ds.getChildrenCount());
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    private void generateQRCodes(int amount) {
        SecureRandom sRandom = new SecureRandom();
        for (int i = 0; i < amount; i++) {
            String text = new BigInteger(130, sRandom).toString(32);
        }
    }
}
