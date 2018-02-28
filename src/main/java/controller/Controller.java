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
 *
 * @author Oscar
 */
public class Controller {
    private final Converter convert = new Converter();
    private final OldRecruitmentDAO oldRecDAO;
    private final NewRecruitmentDAO newRecDAO;
            
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
    
    public List<model_newDB.SupportedLanguage> getLanguages() throws Exception {
        return newRecDAO.getListWithAllContent(model_newDB.SupportedLanguage.class);
    }
    
    public List<model_oldDB.Competence> getOldCompetences() throws Exception {
        return oldRecDAO.getListWithAllContent(model_oldDB.Competence.class);
    }
    
    public void migrateCompetences(List<CompetenceName> competences, Map<Long, CompetenceName> mapping) throws Exception {
        newRecDAO.storeListWithNewContent(competences);
        convert.addNewCompetencesToMap(mapping);
    }
    
    public void migrateRoles() throws Exception {
        newRecDAO.storeListWithNewContent(
                convert.roles(oldRecDAO.getListWithAllContent(model_oldDB.Role.class))
        );
    }
    
    public void migratePersons() throws Exception {
        newRecDAO.storeListWithNewContent(
                convert.persons(oldRecDAO.getListWithAllContent(model_oldDB.Person.class))
        );
    }
    
    public void migrateCompetenceProfiles() throws Exception {
        newRecDAO.storeListWithNewContent(
                convert.competenceProfiles(oldRecDAO.getListWithAllContent(model_oldDB.CompetenceProfile.class))
        );
    }
    
    public void migrateAvailabilities() throws Exception {
        newRecDAO.storeListWithNewContent(
                convert.availabilities(oldRecDAO.getListWithAllContent(model_oldDB.Availability.class))
        );
    }
    
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
    
    public void doneWithDatabases() throws Exception {
        oldRecDAO.closeConnection();
        newRecDAO.closeConnection();
    }
    
}
