/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import java.util.List;
import javax.persistence.EntityManager;
import model.DBMsgConstants;
import model.ExceptionThrower;

/**
 * 
 * @author Oscar
 */
class DatabaseCommunicator {
    private final ExceptionThrower et = new ExceptionThrower();
    
    <T> List<T> getListWithAllContent(EntityManager em, Class<T> contentType, String database) throws Exception {
        String entityName = contentType.getSimpleName();
        String findAll = entityName + ".findAll";
        
        try {
            return em.createNamedQuery(findAll, contentType).getResultList();
        } catch(Exception ex) {
            et.throwException(DBMsgConstants.UNABLE_TO_RETRIEVE + " " + entityName
                    + " table in the " + database);
        }
        
        return null;
    }
    
    void storeListWithNewContent(EntityManager em, List<?> newContent) throws Exception {
        String entityName = newContent.get(0).getClass().getSimpleName();
        
        try {
            newContent.forEach(content -> {
                em.persist(content);
                em.flush();
            });
        } catch(Exception ex) {
            et.throwException(DBMsgConstants.UNALBE_TO_PERSIST + " " + entityName 
                    + " to the " + DBMsgConstants.NEW_DB + ".");
        }
    }

    void closeConnection(EntityManager em) throws Exception {
        try {
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            System.out.println("Error commiting last/closing em: " + ex.getMessage());
            //ex.printStackTrace();
            rollbackDb(em);
            et.throwException(DBMsgConstants.UNABLE_TO_COMMIT);
        }
    }
    
    private void rollbackDb(EntityManager em) {
        try {
            em.getTransaction().rollback();
            em.close();
        } catch(Exception ex) {
            System.out.println("Error rolling back... : " + ex.getMessage());
            //ex.printStackTrace();
        }
    }
    
}
