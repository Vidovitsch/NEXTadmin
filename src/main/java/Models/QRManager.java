package Models;

import Database.DBEventModifier;
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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author David
 */
public class QRManager extends HttpServlet {
    
    private final static int SIZE = 125;
    private final static String FILE_TYPE = "png";
    private Object lock;
    private boolean done = false;
    private boolean generated;
    
    private static Firebase firebase;
    
    public QRManager() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (Firebase) connector.getConnectionObject();
    }
    
    /**
     * Generates a random QR-code for eacht group location on the event
     */
    public void generate() {
        Firebase ref = firebase.child("GroupLocation");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (int i = 0; i < ds.getChildrenCount(); i++) {
                    try {
                        generateQRCode();
                    } catch (WriterException ex) {
                        Logger.getLogger(QRManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                unlockFXThread();
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        lockFXThread();
    }
    
    
    public ArrayList<String> getQRCodes() {
        final ArrayList<String> qrCodes = new ArrayList();
        Firebase ref = firebase.child("QRCode");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot snapshot : ds.getChildren()) {
                    String base64Image = snapshot.getValue().toString();
                    qrCodes.add(base64Image);
                }
                unlockFXThread();
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        lockFXThread();
        
        return qrCodes;
    }
    
    public boolean checkGenerated() {
        Firebase ref = firebase.child("QRCode");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                generated = (ds.getChildrenCount() != 0);
                unlockFXThread();
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        lockFXThread();
                
        return generated;
    }
    
    /**
     * Generates the QR-code image with a random generated key as content
     * @throws WriterException 
     */
    private void generateQRCode() throws WriterException {
        //Generating a random text
        String text = new BigInteger(130, new SecureRandom()).toString(32);
        
        //Creating a QR-code based on the generated text
        Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(text,
                        BarcodeFormat.QR_CODE, SIZE, SIZE, hintMap);
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,
                        BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        
        //Adding graphics to the generated QR-code
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        
        //Saving the generate QR-code to firabase
        saveQRCode(text, image);
    }
    
    /**
     * Saves the QR-code to firebase (under 'QRCode')
     * @param text: key (String format)
     * @param bImage: value (String format) 
     */
    private void saveQRCode(String text, BufferedImage bImage) {
        try {
            Firebase ref = firebase.child("QRCode").child(text);
            ref.setValue(toBase64(bImage));
        } catch (IOException ex) {
            Logger.getLogger(QRManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Converts a buffered image to a base64 format
     * @param bImage
     * @return a base64 string
     * @throws IOException 
     */
    private String toBase64(BufferedImage bImage) throws IOException {
        //Represents data type
        String prefix = "data:image/png;base64,";
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bImage, FILE_TYPE, out);
        byte[] bytes = out.toByteArray();
        byte[] base64bytes = Base64.encodeBase64(bytes);
        String base64Image = new String(base64bytes);
        String result = prefix + base64Image;
        
        return result;
    }

    
    /**
     * Tells a random object to wait while in a loop.
     * The loop stops, and won't cause any unnecessary cpu use.
     */
    private void lockFXThread() {
        lock = new Object();
        synchronized (lock) {
            while (!done) {
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DBEventModifier.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
        done = false;
    }
    
    /**
     * Wakes the lock. The while loop in the method 'lockFXThread' will proceed and break free.
     */
    private void unlockFXThread() {
        synchronized (lock) {
            done = true;
            lock.notifyAll();
        }
    }
}
