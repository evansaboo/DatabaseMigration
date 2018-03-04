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
 * Reprents the availability table in the new database where each row represnts
 * a period of time a specific peron is available.
 *
 * @author Oscar
 */
@Entity
@Table(name = "AVAILABILITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Availability.findAll", query = "SELECT a FROM Availability a")
    , @NamedQuery(name = "Availability.findByAvailabilityId", query = "SELECT a FROM Availability a WHERE a.availabilityId = :availabilityId")
    , @NamedQuery(name = "Availability.findByFromDate", query = "SELECT a FROM Availability a WHERE a.fromDate = :fromDate")
    , @NamedQuery(name = "Availability.findByToDate", query = "SELECT a FROM Availability a WHERE a.toDate = :toDate")})
public class Availability implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AVAILABILITY_ID", nullable = false)
    private Long availabilityId;
    @Basic(optional = false)
    @Column(name = "FROM_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Basic(optional = false)
    @Column(name = "TO_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID", nullable = false)
    @ManyToOne(optional = false)
    private Person personId;

    /**
     * Creates a new availability without setting any variable.
     *
     */
    public Availability() {
    }

    /**
     * Creates a new availability with a specific id.
     * 
     * @param availabilityId the new id.
     */
    public Availability(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    /**
     * Creates a new availability with a specific id and a from date and to date
     * for when the users is available.
     *
     * @param availabilityId the new id.
     * @param fromDate available from this date.
     * @param toDate available to this date.
     */
    public Availability(Long availabilityId, Date fromDate, Date toDate) {
        this.availabilityId = availabilityId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }
    
}
