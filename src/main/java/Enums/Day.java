/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author David
 */
public enum Day {
    
    Ma,
    Di,
    Wo,
    Do,
    Vr;
    
    public Day dateToDate(String date) {
        if (date.equals("11-11-2017")) {
            return Day.Ma;
        } else if (date.equals("12-11-2017")) {
            return Day.Di;
        } else if (date.equals("13-11-2017")) {
            return Day.Wo;
        } else if (date.equals("14-11-2017")) {
            return Day.Do;
        } else {
            return Day.Vr;
        }
    }
};
