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
    
    private ArrayList<String> codes;
    private String generated;

    public ArrayList<String> getCodes() {
        return this.codes;
    }

    public void setCodes(ArrayList<String> codes) {
        this.codes = codes;
    }
    
    public String getGenerated() {
        return this.generated;
    }
    
    public void setGenerated(String generated) {
        this.generated = generated;
    }
}
