/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Arno Dekkers Los
 */
public class ScheduledItemModel {
    private String id;
    private String string;

    public ScheduledItemModel(String id, String string) {
        this.id = id;
        this.string = string;
    }

    public String getId() {
        return id;
    }

    public String getString() {
        return string;
    }
}
