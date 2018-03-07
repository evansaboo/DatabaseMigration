/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.DBMsgConstants;
import model.ExceptionThrower;

/**
 * Handles the connection with the database where the old data is to be migrated
 * and accepts calls to this database from the outside.
 *
 * @author Oscar
 */
public class NewRecruitmentDAO {
    private final ExceptionThrower et = new ExceptionThrower();
    private final DatabaseCommunicator dbCommunicator = new DatabaseCommunicator();
    
    private final EntityManager em = Persistence.createEntityManagerFactory("NewRecPU").createEntityManager();
    
    /**
     * Initializer where a new connection is made to the database and stored in
     * a private variable.
     *
     * @throws Exception if fails to connect to the database.
     */
    public NewRecruitmentDAO() throws Exception {
        try {
            em.getTransaction().begin();
        } catch(Exception ex) {
            et.throwException(DBMsgConstants.UNABLE_TO_CONNECT + " " + DBMsgConstants.NEW_DB);
        }
    }
    
    /**
     * Calls the database communication with this connection and gets a list with
     * all content belonging to a specific entity type, i.e. all content from a table.
     *
     * @param <T>
     * @param contentType
     * @return
     * @throws Exception if the query fails.
     */
    public <T> List<T> getListWithAllContent(Class<T> contentType) throws Exception {
        return dbCommunicator.getListWithAllContent(em, contentType, DBMsgConstants.NEW_DB);
    }
    
    /**
     * Calls the database communication with this connection and stores a new list
     * in the database. 
     *
     * @param newContent
     * @throws Exception if the query fails.
     */
    public void storeListWithNewContent(List<?> newContent) throws Exception {
        dbCommunicator.storeListWithNewContent(em, newContent);
    }

    /**
     * Calls the database communicator to close the connection to this database and
     * flush all data persisted.
     *
     * @throws Exception if unable to close the connection to the database.
     */
    public void closeConnection() throws Exception {
        dbCommunicator.closeConnection(em);
    }

    /**
     * Runs a query on this database of an specified entity type and with a specified
     * query and sets paramaters in the query if any in the entered map.
     *
     * @param <T> Class the entity type belongs to.
     * @param type the entity class.
     * @param query to run in the database table the enitity represents.
     * @param parameters parameters to set in the query.
     * @return T generic entity which is going to be of the same type as the entered
     * class.
     * @throws Exception if the query fails.
     */
    public <T> T getSingleQueryResult(Class<T> type, String query, Map<String, String> parameters) throws Exception {
        String entityName = type.getSimpleName();
        String fullQuery = entityName + "." + query;

        try {
            TypedQuery<T> emQuery = em.createNamedQuery(fullQuery, type);

            parameters.forEach((k, v) -> {
                emQuery.setParameter(k, v);
            });
            
            return emQuery.getSingleResult();
        } catch(RuntimeException rex) {
            throw rex;
        } catch(Exception ex) {
            et.throwException(DBMsgConstants.UNABLE_TO_PERFORM_QUERY + ": " + fullQuery 
                    + " on the " + DBMsgConstants.NEW_DB);
        }

        return null;
    }
    
}
