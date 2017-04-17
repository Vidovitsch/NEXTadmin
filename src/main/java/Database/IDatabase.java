/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author David
 */
public interface IDatabase {
    
    /**
     * Creates a connection with the database
     */
    public void connect();
    
    /**
     * Returns a connection object
     * @return Connection object
     */
    public Object getConnectionObject() throws NullPointerException;
    
    /**
     * Checks the connection with the database
     * @return 
     */
    public boolean checkConnection();
}
