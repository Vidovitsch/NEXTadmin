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
import java.util.ArrayList;

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
                ArrayList<String> groupLocations = new ArrayList();
                for (DataSnapshot key : ds.getChildren()) {
                    //If statemen for safety reasons
                    //Prevents overriding current qr-codes
                    if (!key.getValue().toString().equals("null")) {
                        generateQRCode(key.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    private void generateQRCode(String key) {
        SecureRandom sRandom = new SecureRandom();
        String text = new BigInteger(130, sRandom).toString(32);
        saveToFirebase(key, text);
    }
    
    private void saveToFirebase(String key, String value) {
        Firebase ref = firebase.child("GroupLocation").child(key);
        ref.setValue(value);
    }
}
