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
 * Reprents the person table in the new database where each row represents a 
 * user/applicant.
 *
 * @author Oscar
 */
@Entity
@Table(name = "PERSON", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"USERNAME"})
    , @UniqueConstraint(columnNames = {"SSN"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findByPersonId", query = "SELECT p FROM Person p WHERE p.personId = :personId")
    , @NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE p.name = :name")
    , @NamedQuery(name = "Person.findBySurname", query = "SELECT p FROM Person p WHERE p.surname = :surname")
    , @NamedQuery(name = "Person.findBySsn", query = "SELECT p FROM Person p WHERE p.ssn = :ssn")
    , @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email")
    , @NamedQuery(name = "Person.findByPassword", query = "SELECT p FROM Person p WHERE p.password = :password")
    , @NamedQuery(name = "Person.findByUsername", query = "SELECT p FROM Person p WHERE p.username = :username")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PERSON_ID", nullable = false)
    private Long personId;
    @Basic(optional = false)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @Basic(optional = false)
    @Column(name = "SURNAME", nullable = false, length = 255)
    private String surname;
    @Basic(optional = false)
    @Column(name = "SSN", nullable = false, length = 255)
    private String ssn;
    @Basic(optional = false)
    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;
    @Basic(optional = false)
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;
    @Basic(optional = false)
    @Column(name = "USERNAME", nullable = false, length = 255)
    private String username;
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", nullable = false)
    @ManyToOne(optional = false)
    private Role roleId;

    /**
     * Creates a new person without setting any variable.
     * 
     */
    public Person() {
    }

    /**
     * Creates a new person and sets the id.
     *
     * @param personId
     */
    public Person(Long personId) {
        this.personId = personId;
    }

    /**
     * Creates a new person and sets all the columns this entity represent.
     *
     * @param personId of the new user.
     * @param name of the new user.
     * @param surname of the new user.
     * @param ssn of the new user.
     * @param email of the new user.
     * @param password of the new user.
     * @param username of the new user.
     */
    public Person(Long personId, String name, String surname, String ssn, String email, String password, String username) {
        this.personId = personId;
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }
    
}
