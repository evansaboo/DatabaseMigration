/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Oscar
 */
public class NewDatabaseConstants {
    public static final String ROLE_APPLICANT = "Applicant";
    public static final String ROLE_RECRUITER = "Recruiter";
    public static final String DATA_NOT_PRESENT = "no data present at migration";
    
    public static final String[][] SUPPORTED_LANGUAGES_WITH_STATUS_NAMES = {
        {"sv", "Accepterad", "Nekad", "Väntar"},
        {"en", "Accepted", "Rejected", "Pending"}
    };
    
}
