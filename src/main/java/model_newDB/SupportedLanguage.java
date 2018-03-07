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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Reprents the supported_language table in the new database where each row 
 * represents a language the database can handle.
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

    /**
     * Creates a new supported language without setting any variable.
     *
     */
    public SupportedLanguage() {
    }

    /**
     * Creates a new supported language with a specific id.
     *
     * @param supportedLanguageId the new id.
     */
    public SupportedLanguage(Long supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

    /**
     * Creates a new supported language with a specific id and the language/locale
     * this supported lanugage is for.
     *
     * @param supportedLanguageId the new id.
     * @param locale the locale of this supported language.
     */
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
    
}
