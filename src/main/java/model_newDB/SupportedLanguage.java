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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Oscar
 */
@Entity
@Table(name = "SUPPORTED_LANGUAGE", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"LOCALE"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupportedLanguage.findAll", query = "SELECT s FROM SupportedLanguage s")
    , @NamedQuery(name = "SupportedLanguage.findBySupportedLanguageId", query = "SELECT s FROM SupportedLanguage s WHERE s.supportedLanguageId = :supportedLanguageId")
    , @NamedQuery(name = "SupportedLanguage.findByLocale", query = "SELECT s FROM SupportedLanguage s WHERE s.locale = :locale")})
public class SupportedLanguage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUPPORTED_LANGUAGE_ID", nullable = false)
    private Long supportedLanguageId;
    @Basic(optional = false)
    @Column(name = "LOCALE", nullable = false, length = 255)
    private String locale;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supportedLanguage")
    private List<CompetenceName> competenceNameList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supportedLanguage")
    private List<StatusName> statusNameList;

    public SupportedLanguage() {
    }

    public SupportedLanguage(Long supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

    public SupportedLanguage(Long supportedLanguageId, String locale) {
        this.supportedLanguageId = supportedLanguageId;
        this.locale = locale;
    }

    public Long getSupportedLanguageId() {
        return supportedLanguageId;
    }

    public void setSupportedLanguageId(Long supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @XmlTransient
    public List<CompetenceName> getCompetenceNameList() {
        return competenceNameList;
    }

    public void setCompetenceNameList(List<CompetenceName> competenceNameList) {
        this.competenceNameList = competenceNameList;
    }

    @XmlTransient
    public List<StatusName> getStatusNameList() {
        return statusNameList;
    }

    public void setStatusNameList(List<StatusName> statusNameList) {
        this.statusNameList = statusNameList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supportedLanguageId != null ? supportedLanguageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupportedLanguage)) {
            return false;
        }
        SupportedLanguage other = (SupportedLanguage) object;
        if ((this.supportedLanguageId == null && other.supportedLanguageId != null) || (this.supportedLanguageId != null && !this.supportedLanguageId.equals(other.supportedLanguageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model_newDB.SupportedLanguage[ supportedLanguageId=" + supportedLanguageId + " ]";
    }
    
}
