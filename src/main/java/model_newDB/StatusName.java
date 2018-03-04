/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_newDB;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Reprents the status_name table in the new database where each row represents 
 * a status in a specific language.
 *
 * @author Oscar
 */
@Entity
@Table(name = "STATUS_NAME", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"STATUS_ID", "NAME", "SUPPORTED_LANGUAGE"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusName.findAll", query = "SELECT s FROM StatusName s")
    , @NamedQuery(name = "StatusName.findByStatusNameId", query = "SELECT s FROM StatusName s WHERE s.statusNameId = :statusNameId")
    , @NamedQuery(name = "StatusName.findByStatusId", query = "SELECT s FROM StatusName s WHERE s.statusId = :statusId")
    , @NamedQuery(name = "StatusName.findByName", query = "SELECT s FROM StatusName s WHERE s.name = :name")})
public class StatusName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STATUS_NAME_ID", nullable = false)
    private Long statusNameId;
    @Basic(optional = false)
    @Column(name = "STATUS_ID", nullable = false)
    private long statusId;
    @Basic(optional = false)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusName")
    private List<Applications> applicationsList;
    @JoinColumn(name = "SUPPORTED_LANGUAGE", referencedColumnName = "SUPPORTED_LANGUAGE_ID", nullable = false)
    @ManyToOne(optional = false)
    private SupportedLanguage supportedLanguage;

    /**
     * Creates a new status name without setting any variable.
     *
     */
    public StatusName() {
    }

    /**
     * Creates a new status name with a specific id.
     *
     * @param statusNameId the new id.
     */
    public StatusName(Long statusNameId) {
        this.statusNameId = statusNameId;
    }

    /**
     * Creates a new status name with a specific id and a status id with is same 
     * for this type of status in all languages and a name in this this specific 
     * language.
     *
     * @param statusNameId the new id.
     * @param statusId the id for this type of status.
     * @param name the name of the status in a specifc language.
     */
    public StatusName(Long statusNameId, long statusId, String name) {
        this.statusNameId = statusNameId;
        this.statusId = statusId;
        this.name = name;
    }

    public Long getStatusNameId() {
        return statusNameId;
    }

    public void setStatusNameId(Long statusNameId) {
        this.statusNameId = statusNameId;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Applications> getApplicationsList() {
        return applicationsList;
    }

    public void setApplicationsList(List<Applications> applicationsList) {
        this.applicationsList = applicationsList;
    }

    public SupportedLanguage getSupportedLanguage() {
        return supportedLanguage;
    }

    public void setSupportedLanguage(SupportedLanguage supportedLanguage) {
        this.supportedLanguage = supportedLanguage;
    }
    
}
