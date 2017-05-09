/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import java.util.ArrayList;


/**
 *
 * @author David
 */
public class QRViewModel {
    
    private ArrayList<String> qrCodes;
    private String generated;

    public ArrayList<String> getCodes() {
        return qrCodes;
    }

    public void setCodesGenerated(ArrayList<String> qrCodes) {
        this.qrCodes = qrCodes;
    }
    
    public String getGenerated() {
        return this.generated;
    }
    
    public void setGenerated(String generated) {
        if (!generated.equals("false") || !generated.equals("true")) {
            generated = "false";
        } else {
            generated = "true";
        }
    }
}
