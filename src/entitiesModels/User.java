/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesModels;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Francisco Romero Alonso
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String login;

    private final SimpleStringProperty email;

    private  final SimpleStringProperty fullname;

    private UserStatus status;

    private UserPrivilege privilege;

    private String password;

    private Collection<Document> documents;

    private final SimpleObjectProperty<Date> lastAccess;
    
    private Date bDate;

    private Date lastPasswordChange;

    private byte[] photo;

    private final SimpleObjectProperty<Company> company;

    public User() {
        this.email = new SimpleStringProperty();
        this.fullname = new SimpleStringProperty();
        this.lastAccess = new SimpleObjectProperty();
        this.company = new SimpleObjectProperty();
    }

    public User(int id, String login, String email, String fullname, UserStatus status, UserPrivilege privilege, String password, Collection<Document> documents, Date lastAccess, Date bDate, Date lastPasswordChange, byte[] photo, Company company) {
        this.id = id;
        this.login = login;
        this.email = new SimpleStringProperty(email);
        this.fullname = new SimpleStringProperty(fullname);
        this.status = status;
        this.privilege = privilege;
        this.password = password;
        this.documents = documents;
        this.lastAccess = new SimpleObjectProperty(lastAccess);
        this.bDate = bDate;
        this.lastPasswordChange = lastPasswordChange;
        this.photo = photo;
        this.company = new SimpleObjectProperty(company);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<Document> documents) {
        this.documents = documents;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getFullname() {
        return this.fullname.get();
    }

    public void setFullname(String fullname) {
        this.fullname.set(fullname);
    }

    public Date getLastAccess() {
        return this.lastAccess.get();
    }
    
    public void setLastAccess(Date lastAccess){
        this.lastAccess.set(lastAccess);
    }

    public Company getCompany() {
        return this.company.get();
    }
    
    public void setCompany(Company company) {
        this.company.set(company);
    }

}