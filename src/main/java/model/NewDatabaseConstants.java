/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Constants that represent data to be used in the new database.
 *
 * @author Oscar
 */
class NewDatabaseConstants {

    /**
     * String value of the applicant role.
     */
    static final String ROLE_APPLICANT = "Applicant";

    /**
     *  String value of the recruiter role.
     */
    static final String ROLE_RECRUITER = "Recruiter";

    /**
     *  String value when no data was present in the old database.
     */
    static final String DATA_NOT_PRESENT = "no data present at migration";
    
    /**
     *  Default values that gets entered in the new databse status_name table 
     * with their languages and differente statuses.
     */
    static final String[][] SUPPORTED_LANGUAGES_WITH_STATUS_NAMES = {
        {"sv", "Accepterad", "Nekad", "Väntar"},
        {"en", "Accepted", "Rejected", "Pending"}
    };
    
}
