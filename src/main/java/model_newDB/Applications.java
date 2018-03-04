/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_newDB;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Reprents the applications table in the new database where each row represents
 * an application made.
 *
 * @author Oscar
 */
@Entity
@Table(name = "APPLICATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Applications.findAll", query = "SELECT a FROM Applications a")
    , @NamedQuery(name = "Applications.findByApplicationId", query = "SELECT a FROM Applications a WHERE a.applicationId = :applicationId")
    , @NamedQuery(name = "Applications.findByRegistrationDate", query = "SELECT a FROM Applications a WHERE a.registrationDate = :registrationDate")})
public class Applications implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "APPLICATION_ID", nullable = false)
    private Long applicationId;
    @Basic(optional = false)
    @Column(name = "REGISTRATION_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID", nullable = false)
    @ManyToOne(optional = false)
    private Person personId;
    @JoinColumn(name = "STATUS_NAME", referencedColumnName = "STATUS_NAME_ID", nullable = false)
    @ManyToOne(optional = false)
    private StatusName statusName;

    /**
     * Creates a new application without setting any variable.
     *
     */
    public Applications() {
    }

    /**
     * Creates a new application with a specific id.
     *
     * @param applicationId the new id.
     */
    public Applications(Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Creates a new application with a specific id and a registration date.
     *
     * @param applicationId the new id.
     * @param registrationDate the date the application was registered.
     */
    public Applications(Long applicationId, Date registrationDate) {
        this.applicationId = applicationId;
        this.registrationDate = registrationDate;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public StatusName getStatusName() {
        return statusName;
    }

    public void setStatusName(StatusName statusName) {
        this.statusName = statusName;
    }
    
}
