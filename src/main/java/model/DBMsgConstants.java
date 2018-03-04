/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Constants used by the database handlers with error messages when different things
 * go wrong or are unsuccessful. 
 *
 * @author Oscar
 */
public class DBMsgConstants {

    /**
     * Error message when the database handler is unable to connect to the database.
     */
    public static final String UNABLE_TO_CONNECT = "Unable to connect to the";

    /**
     * Error message when the database handler is unable to persist new data to the database.
     */
    public static final String UNALBE_TO_PERSIST = "Unable to persist a new";

    /**
     * Error message when the database handler is unable to commit new data to the database.
     */
    public static final String UNABLE_TO_COMMIT = "The storing to the new database failed. Please try again or validate the data";

    /**
     * Error message when the database handler is unable to fetch data from the database.
     */
    public static final String UNABLE_TO_RETRIEVE = "Unable to retrieve data from the";

    /**
     * Error message when the database handler is unable to perform a query on the database.
     */
    public static final String UNABLE_TO_PERFORM_QUERY = "Unable to perform the query";
    
    /**
     * String value used to represent the old database.
     */
    public static final String OLD_DB = "old recruitment database";

    /**
     * String value used to represent the new database.
     */
    public static final String NEW_DB = "new recruitment database";
    
}
