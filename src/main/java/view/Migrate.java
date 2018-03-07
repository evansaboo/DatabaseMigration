/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model_newDB.CompetenceName;
import model_newDB.SupportedLanguage;
import model_oldDB.Competence;


/**
 * Represents the migration of the old database to the new database. All migration
 * is done from here and this is the main method in the application. 
 * 
 * @author Oscar
 */
public class Migrate {
    private static final Scanner STD_IN = new Scanner(System.in).useDelimiter("\\n");
    
    /**
     * Main method that runs when program is run.
     * 
     * @param args runtime arguments, none expected.
     */
    public static void main(String[] args) {
        try {
            Controller controller = new Controller();
            
            printStartMessage();
            
            List<SupportedLanguage> languages = controller.getLanguages();
            List<Competence> oldCompetences = controller.getOldCompetences();
            List<CompetenceName> newCompetences = new ArrayList<>();
            Map<Long, CompetenceName> newCompetencesFromPreviousId = new HashMap<>();

            for(int i = 0; i < oldCompetences.size(); i++) {
                
                final long newCompId = i + 1;
                String compName = oldCompetences.get(i).getName();
                final long compId = oldCompetences.get(i).getCompetenceId();

                languages.forEach(lang -> {
                    boolean valid;
                    String translation = "";

                    do {
                        String language = lang.getLocale();
                        translation = translate(compName, language);
                        valid = validateInputTranslation(compName, translation, language);
                    } while (!valid);
                    
                    CompetenceName newComp = new CompetenceName();
                    newComp.setName(translation);
                    newComp.setCompetenceId(newCompId);
                    newComp.setSupportedLanguage(lang);
                    
                    newCompetences.add(newComp);
                    newCompetencesFromPreviousId.putIfAbsent(compId, newComp);
                });

                
            }
        
            printStatus("Comeptence", "beginning");
            controller.migrateCompetences(newCompetences, newCompetencesFromPreviousId);
            printStatus("Comeptence", "done");

            printStatus("Role", "beginning");
            controller.migrateRoles();
            printStatus("Role", "done");
            
            printStatus("Person", "beginning");
            controller.migratePersons();
            printStatus("Person", "done");
            
            printStatus("ComeptenceProfile", "beginning");
            controller.migrateCompetenceProfiles();
            printStatus("ComeptenceProfile", "done");
            
            printStatus("Availability", "beginning");
            controller.migrateAvailabilities();
            printStatus("Availability", "done");
            
            System.out.println("Start creating new applications");
            controller.createApplications();
            System.out.println("The new pplications have now been created");
            
            controller.doneWithDatabases();
            printStopMessage();
        } catch(Exception ex) {
            printErrorAndQuit(ex.getMessage());
        }
    }
    
    private static String translate(String original, String language) {
        String translation = "";
        
        while(translation.isEmpty()) {
            System.out.print("Translate <" + original + "> to the language <" + language + ">: ");
            translation = STD_IN.next();
        }
        
        return translation;
    }
    
    private static boolean validateInputTranslation(String original, String translation, String language) {
        String decision = "";
        
        do {
            System.out.print("Are you sure that <" + original + "> should be stored as <" 
                    + translation + "> in the language <" + language + ">? (y/n): ");
            decision = STD_IN.next();
        } while(!decision.equals("n") && !decision.equals("y"));
        
        
        return decision.equals("y");
    }
    
    private static void printStatus(String table, String status) {
        System.out.println("Migration of table: " + table + ", is now " + status);
    }
    
    private static void printErrorAndQuit(String msg) {
        System.out.println("An error occured while trying to migrate.");
        System.out.println("The error message is: " + msg + ".");
        System.out.println("The migration tool is now shutting down without keeping any changes made.");
        System.exit(0);
    }

    private static void printStartMessage() {
        System.out.println("\n -- Welcome to the database migration tool. You are first "
                + "going to begin with a manual translation of the competences. "
                + "Once that is done, the migration is automated and you get a status "
                + "update for each table. -- \n");
    }
    
    private static void printStopMessage() {
        System.out.println("\n -- The migration of the old database is now done and the program "
                + "is going to shut down. Good bye! -- \n");
    }
    
}
