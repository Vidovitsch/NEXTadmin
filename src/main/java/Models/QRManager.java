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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.random;
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
    
    private static Firebase firebase;
    
    public QRManager() {
        FBConnector connector = FBConnector.getInstance();
        connector.connect();
        firebase = (Firebase) connector.getConnectionObject();
    }
    
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
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    private void generateQRCode() throws WriterException {
        String text = new BigInteger(130, new SecureRandom()).toString(32);
        
        Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(text,
                        BarcodeFormat.QR_CODE, SIZE, SIZE, hintMap);
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,
                        BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
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
        
        saveQRCode(text, image);
    }
    
    private void saveQRCode(String text, BufferedImage bImage) {
        try {
            Firebase ref = firebase.child("QRCode").child(text);
            ref.setValue(toBase64(bImage));
        } catch (IOException ex) {
            Logger.getLogger(QRManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String toBase64(BufferedImage bImage) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bImage, FILE_TYPE, out);
        byte[] bytes = out.toByteArray();
        byte[] base64bytes = Base64.encodeBase64(bytes);
        String base64Image = new String(base64bytes);
        
        return base64Image;
    }
}
