/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_newDB;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Reprents the competence_name table in the new database where each row represents
 * a competence in a specific language.
 *
 * @author Oscar
 */
@Entity
@Table(name = "COMPETENCE_NAME", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"COMPETENCE_ID", "NAME", "SUPPORTED_LANGUAGE"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompetenceName.findAll", query = "SELECT c FROM CompetenceName c")
    , @NamedQuery(name = "CompetenceName.findByCompetenceNameId", query = "SELECT c FROM CompetenceName c WHERE c.competenceNameId = :competenceNameId")
    , @NamedQuery(name = "CompetenceName.findByCompetenceId", query = "SELECT c FROM CompetenceName c WHERE c.competenceId = :competenceId")
    , @NamedQuery(name = "CompetenceName.findByName", query = "SELECT c FROM CompetenceName c WHERE c.name = :name")})
public class CompetenceName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COMPETENCE_NAME_ID", nullable = false)
    private Long competenceNameId;
    @Basic(optional = false)
    @Column(name = "COMPETENCE_ID", nullable = false)
    private long competenceId;
    @Basic(optional = false)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @JoinColumn(name = "SUPPORTED_LANGUAGE", referencedColumnName = "SUPPORTED_LANGUAGE_ID", nullable = false)
    @ManyToOne(optional = false)
    private SupportedLanguage supportedLanguage;

    /**
     * Creates a new competence name without setting any varaible.
     *
     */
    public CompetenceName() {
    }

    /**
     * Creates a new competence name with a specific id.
     *
     * @param competenceNameId the new id.
     */
    public CompetenceName(Long competenceNameId) {
        this.competenceNameId = competenceNameId;
    }

    /**
     * Creates a new competence name with a specific id, the competence name id 
     * which is the same for this competence no matter language and a name.
     *
     * @param competenceNameId the new id.
     * @param competenceId the new id that is same for all languages.
     * @param name the name in this specific language.
     */
    public CompetenceName(Long competenceNameId, long competenceId, String name) {
        this.competenceNameId = competenceNameId;
        this.competenceId = competenceId;
        this.name = name;
    }

    public Long getCompetenceNameId() {
        return competenceNameId;
    }

    public void setCompetenceNameId(Long competenceNameId) {
        this.competenceNameId = competenceNameId;
    }

    public long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(long competenceId) {
        this.competenceId = competenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupportedLanguage getSupportedLanguage() {
        return supportedLanguage;
    }

    public void setSupportedLanguage(SupportedLanguage supportedLanguage) {
        this.supportedLanguage = supportedLanguage;
    }
    
}
