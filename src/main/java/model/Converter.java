/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import model_newDB.Applications;
import model_newDB.Availability;
import model_newDB.CompetenceName;
import model_newDB.CompetenceProfile;
import model_newDB.Person;
import model_newDB.Role;
import model_newDB.StatusName;
import model_newDB.SupportedLanguage;

/**
 *
 * @author Oscar
 */
public class Converter {
    private final Map<Long, CompetenceName> newCompetencesFromPreviousId = new HashMap<>();
    private final Map<Long, Role> newRolesFromPreviousId = new HashMap<>();
    private final Map<Long, Person> newPersonsFromPreviousId = new HashMap<>();
    private final List<Person> newPersonsThatAreApplicants = new ArrayList<>();
    
    public void addNewCompetencesToMap(Map<Long, CompetenceName> competences) {
        newCompetencesFromPreviousId.putAll(competences);
    }
    
    public List<Availability> availabilities(List<model_oldDB.Availability> availabilities) {
        return availabilities.stream().map(oldAvailability -> {
            Availability availability = new Availability();
            
            availability.setPersonId(newPersonsFromPreviousId.get(oldAvailability.getPersonId().getPersonId()));
            availability.setFromDate(oldAvailability.getFromDate());
            availability.setToDate(oldAvailability.getToDate());
            
            return availability;
        }).collect(Collectors.toList());
    }
    
    public List<CompetenceProfile> competenceProfiles(List<model_oldDB.CompetenceProfile> profiles) {
        return profiles.stream().map(oldProfile -> {
            CompetenceProfile profile = new CompetenceProfile();
            
            profile.setPersonId(newPersonsFromPreviousId.get(oldProfile.getPersonId().getPersonId()));
            profile.setYearsOfExperience(oldProfile.getYearsOfExperience());
            profile.setCompetenceId(newCompetencesFromPreviousId.get(oldProfile.getCompetenceId().getCompetenceId()));
            
            return profile;
        }).collect(Collectors.toList());
    }
    
    public List<Role> roles(List<model_oldDB.Role> roles) {
        return roles.stream().map((oldRole) -> {
            Role role =  new Role();
            
            if(oldRole.getName().startsWith("r") || oldRole.getName().startsWith("R")) {
                role.setName(NewDatabaseConstants.ROLE_RECRUITER);
            } else {
                role.setName(NewDatabaseConstants.ROLE_APPLICANT);
            }
            
            newRolesFromPreviousId.put(oldRole.getRoleId(), role);
            return role;
        }).collect(Collectors.toList());
        
    }
    
    public List<Person> persons(List<model_oldDB.Person> oldPersons) {
        return oldPersons.stream().map(oldPerson -> { 
            Person person = new Person();
            
            person.setName(ifEmptyUseDefault(oldPerson.getName(), false));
            person.setSurname(ifEmptyUseDefault(oldPerson.getSurname(), false));
            person.setSsn(ifEmptyUseDefault(oldPerson.getSsn(), true));
                        
            String username = oldPerson.getUsername();
            String password = oldPerson.getPassword();
            String email = oldPerson.getEmail();

            boolean changesMade = false;
            
            if(!validInput(username)) {
                username = generateRandomString(130);
                password = generateNewSuperSecretPassword(username);
                changesMade = true;
            } else if(!validInput(password)) {
                password = generateNewSuperSecretPassword(username);
                changesMade = true;
            }
            
            if(changesMade && validInput(email)) {
                sendEmailAboutAccountUpdate(email, username, password);
            } else {
                email = generateRandomDefaultString();
            }
            
            person.setUsername(username);
            person.setPassword(password);
            person.setEmail(ifEmptyUseDefault(email, false));
            Role role = newRolesFromPreviousId.get(oldPerson.getRoleId().getRoleId());
            person.setRoleId(role);
            
            if(role.getName().equals(NewDatabaseConstants.ROLE_APPLICANT)) {
                newPersonsThatAreApplicants.add(person);
            }
            
            newPersonsFromPreviousId.put(oldPerson.getPersonId(), person);
            return person;
        }).collect(Collectors.toList());
    }
    
    private void sendEmailAboutAccountUpdate(String email, String username, String password) {
        System.out.println("\nTo: " + email);
        System.out.println("From: Recruitment@Services.now");
        System.out.println("\nHi we have made some changes to our services. To be able to upload "
                + "new content please use the following credentials to log in:\n");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("\nWe hope to see you soon!\n");
    }
    
    private String generateNewSuperSecretPassword(String username) {
        return username;
    }
    
    private String ifEmptyUseDefault(String current, boolean unique) {
        if(unique) {
            return validInput(current) ? current : generateRandomDefaultString();
        } else {
            return validInput(current) ? current : NewDatabaseConstants.DATA_NOT_PRESENT;
        }
    }
    
    private String generateRandomDefaultString() {
        return NewDatabaseConstants.DATA_NOT_PRESENT + ": " + generateRandomString(130);
    }
    
    private String generateRandomString(int bits) {
        Random random = new SecureRandom();
        return new BigInteger(bits, random).toString(32);
    }

    private boolean validInput(String input) {
        return input != null && !input.isEmpty();
    }

    public List<Applications> createApplications(StatusName status) {
        Date registrationDate = new Date(System.currentTimeMillis());
        
        return newPersonsThatAreApplicants.stream().map(applicant -> {
            Applications application = new Applications();
            
            application.setPersonId(applicant);
            application.setRegistrationDate(registrationDate);
            application.setStatusName(status);
            
            return application;
        }).collect(Collectors.toList());
    }

    public List<?> createApplications() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<List<?>> getIntialData() {
        List<List<?>> data = new ArrayList<>();
        List<SupportedLanguage> languages = new ArrayList<>();
        List<StatusName> statuses = new ArrayList<>();
        
        String[][] langAndStatus = NewDatabaseConstants.SUPPORTED_LANGUAGES_WITH_STATUS_NAMES;
        
        for(String[] langAndStatu : langAndStatus) {
            SupportedLanguage language = new SupportedLanguage();
            language.setLocale(langAndStatu[0]);
            languages.add(language);
           
            for (int j = 1; j < langAndStatu.length; j++) {
                statuses.add(getNewStatus(j, langAndStatu[j], language));
            }
            
        }
        
        data.add(languages);
        data.add(statuses);
        
        return data;
    }
    
    private StatusName getNewStatus(int id, String name, SupportedLanguage language) {
        StatusName status = new StatusName();
        
        status.setStatusId(id);
        status.setName(name);
        status.setSupportedLanguage(language);
        
        return status;
    }

}
