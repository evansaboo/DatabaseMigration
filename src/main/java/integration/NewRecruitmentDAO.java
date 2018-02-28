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
 *
 * @author Oscar
 */
public class NewRecruitmentDAO {
    private final ExceptionThrower et = new ExceptionThrower();
    private final DatabaseCommunicator dbCommunicator = new DatabaseCommunicator();
    
    private final EntityManager em = Persistence.createEntityManagerFactory("NewRecPU").createEntityManager();
    

    public NewRecruitmentDAO() throws Exception {
        try {
            em.getTransaction().begin();
        } catch(Exception ex) {
            et.throwException(DBMsgConstants.UNABLE_TO_CONNECT + " " + DBMsgConstants.NEW_DB);
        }
    }
    
    public <T> List<T> getListWithAllContent(Class<T> contentType) throws Exception {
        return dbCommunicator.getListWithAllContent(em, contentType, DBMsgConstants.NEW_DB);
    }
    
    public void storeListWithNewContent(List<?> newContent) throws Exception {
        dbCommunicator.storeListWithNewContent(em, newContent);
    }

    public void closeConnection() throws Exception {
        dbCommunicator.closeConnection(em);
    }

    public <T> T getSingleQueryResult(Class<T> type, String query, Map<String, String> parameters) throws Exception {
        String entityName = type.getSimpleName();
        String fullQuery = entityName + "." + query;

        try {
            TypedQuery<T> emQuery = em.createNamedQuery(fullQuery, type);

            parameters.forEach((k, v) -> {
                emQuery.setParameter(k, v);
            });
            
            return emQuery.getSingleResult();
        } catch (Exception e) {
            et.throwException(DBMsgConstants.UNABLE_TO_PERFORM_QUERY + ": " + fullQuery 
                    + " on the " + DBMsgConstants.NEW_DB);
        }

        return null;
    }
    
}
