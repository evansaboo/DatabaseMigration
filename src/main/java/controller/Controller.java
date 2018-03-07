/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import integration.NewRecruitmentDAO;
import integration.OldRecruitmentDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Converter;
import model_newDB.CompetenceName;

/**
 * Handles calls from the view and uses the models and integration to handle 
 * the requests and respond with the right data. 
 * 
 * @author Oscar
 */
public class Controller {
    private final Converter convert = new Converter();
    private final OldRecruitmentDAO oldRecDAO;
    private final NewRecruitmentDAO newRecDAO;
            
    /**
     * Initializer where the database handlers are created and the new database
     * is initialized with data needed for the migration.
     *
     * @throws Exception if something goes wrong.
     */
    public Controller() throws Exception {
        oldRecDAO = new OldRecruitmentDAO();
        newRecDAO = new NewRecruitmentDAO();
        initializeNewRecruitmentDb();
    }
    
    private void initializeNewRecruitmentDb() throws Exception {
        for(List<?> data : convert.getIntialData()) {
            newRecDAO.storeListWithNewContent(data);
        }
    }
    
    /**
     * Retreives the supported languages from the new database and returns them.
     *
     * @return list with supported languages.
     * @throws Exception if something goes wrong.
     */
    public List<model_newDB.SupportedLanguage> getLanguages() throws Exception {
        return newRecDAO.getListWithAllContent(model_newDB.SupportedLanguage.class);
    }
    
    /**
     * Retreives the competences from the old database and returns them.
     *
     * @return list with the old competences.
     * @throws Exception if something goes wrong.
     */
    public List<model_oldDB.Competence> getOldCompetences() throws Exception {
        return oldRecDAO.getListWithAllContent(model_oldDB.Competence.class);
    }
    
    /**
     * Receives new competences to be stored and a map of the old id with the new 
     * competence. Stores the new competences in the new database.
     *
     * @param competences list with new competences to store.
     * @param mapping map with the id of the old competence with the new competence 
     * name object.
     * @throws Exception if something goes wrong.
     */
    public void migrateCompetences(List<CompetenceName> competences, Map<Long, CompetenceName> mapping) throws Exception {
        newRecDAO.storeListWithNewContent(competences);
        convert.addNewCompetencesToMap(mapping);
    }
    
    /**
     * Retreives the roles from the old database and converts and stores them in
     * the new database.
     *
     * @throws Exception if something goes wrong.
     */
    public void migrateRoles() throws Exception {
        newRecDAO.storeListWithNewContent(
                convert.roles(oldRecDAO.getListWithAllContent(model_oldDB.Role.class))
        );
    }
    
    /**
     * Retreives the persons from the old database and converts and stores them in
     * the new database.
     *
     * @throws Exception if something goes wrong.
     */
    public void migratePersons() throws Exception {
        newRecDAO.storeListWithNewContent(
                convert.persons(oldRecDAO.getListWithAllContent(model_oldDB.Person.class))
        );
    }
    
    /**
     * Retreives the competence profiles from the old database and converts and 
     * stores them in the new database.
     *
     * @throws Exception if something goes wrong.
     */
    public void migrateCompetenceProfiles() throws Exception {
        newRecDAO.storeListWithNewContent(
                convert.competenceProfiles(oldRecDAO.getListWithAllContent(model_oldDB.CompetenceProfile.class))
        );
    }
    
    /**
     * Retreives the availabilites from the old database and converts and stores 
     * them in the new database.
     *
     * @throws Exception if something goes wrong.
     */
    public void migrateAvailabilities() throws Exception {
        newRecDAO.storeListWithNewContent(
                convert.availabilities(oldRecDAO.getListWithAllContent(model_oldDB.Availability.class))
        );
    }
    
    /**
     * Creates new applications with status pending and stores them in the new
     * database.
     * 
     * @throws Exception if anything goes wrong.
     */
    public void createApplications() throws Exception {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", "Pending");
        
        newRecDAO.storeListWithNewContent(
                convert.createApplications(newRecDAO.getSingleQueryResult(
                        model_newDB.StatusName.class, 
                        "findByName", 
                        parameters)
                )
        );
    }
    
    /**
     * Notifies the databases that the communication is done and no more queries
     * are going to happen.
     * 
     * @throws Exception if something goes wrong.
     */
    public void doneWithDatabases() throws Exception {
        oldRecDAO.closeConnection();
        newRecDAO.closeConnection();
    }
    
}
