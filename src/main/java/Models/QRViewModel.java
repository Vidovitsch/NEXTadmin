/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import java.util.ArrayList;


/**
 * This method is used to save a QR code in a format accessible to the GUI
 * @author David
 */
public class QRViewModel {
    
    private ArrayList<String> codes;
    private String generated;

    /**
     * Get the value of codes
     *
     * @return the value of codes
     */
    public ArrayList<String> getCodes() {
        return this.codes;
    }

    /**
     * Set the value of codes
     *
     * @param codes new value of codes
     */
    public void setCodes(ArrayList<String> codes) {
        this.codes = codes;
    }
    
    /**
     * Get the value of generated
     *
     * @return the value of generated
     */
    public String getGenerated() {
        return this.generated;
    }
    
    /**
     * Set the value of generated
     *
     * @param generated new value of generated
     */
    public void setGenerated(String generated) {
        this.generated = generated;
    }
}
