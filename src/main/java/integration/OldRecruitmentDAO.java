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
 *
 * @author Oscar
 */
public class OldRecruitmentDAO {
    private final ExceptionThrower et = new ExceptionThrower();
    private final DatabaseCommunicator dbCommunicator = new DatabaseCommunicator();
    
    private final EntityManager em = Persistence.createEntityManagerFactory("OldRecPU").createEntityManager();

    public OldRecruitmentDAO() throws Exception {
        try {
            em.getTransaction().begin();
        } catch(Exception ex) {
            et.throwException(DBMsgConstants.UNABLE_TO_CONNECT + " " + DBMsgConstants.OLD_DB);
        }
    }

    public <T> List<T> getListWithAllContent(Class<T> contentType) throws Exception {
        return dbCommunicator.getListWithAllContent(em, contentType, DBMsgConstants.OLD_DB);
    }
    
    public void closeConnection() throws Exception {
        dbCommunicator.closeConnection(em);
    }
}
