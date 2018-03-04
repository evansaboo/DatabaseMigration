/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import model.DBMsgConstants;
import model.ExceptionThrower;

/**
 * Handles the connection with the database from where the old data comes and
 * accepts calls to this database from the outside..
 *
 * @author Oscar
 */
public class OldRecruitmentDAO {
    private final ExceptionThrower et = new ExceptionThrower();
    private final DatabaseCommunicator dbCommunicator = new DatabaseCommunicator();
    
    private final EntityManager em = Persistence.createEntityManagerFactory("OldRecPU").createEntityManager();

    /**
     * Initializer where a new connection is made to the database and stored in
     * a private variable.
     *
     * @throws Exception if fails to connect to the database.
     */
    public OldRecruitmentDAO() throws Exception {
        try {
            em.getTransaction().begin();
        } catch(Exception ex) {
            et.throwException(DBMsgConstants.UNABLE_TO_CONNECT + " " + DBMsgConstants.OLD_DB);
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
        return dbCommunicator.getListWithAllContent(em, contentType, DBMsgConstants.OLD_DB);
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
}
