/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesModels;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Francisco Romero Alonso
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private final SimpleIntegerProperty id;

    private final SimpleStringProperty login;

    private final SimpleStringProperty email;

    private final SimpleStringProperty fullname;

    private final SimpleObjectProperty<UserStatus> status;

    private final SimpleObjectProperty<UserPrivilege> privilege;

    private final SimpleStringProperty password;

    private final SimpleListProperty<Document> documents;

    private final SimpleObjectProperty<Date> lastAccess;
    
    private final SimpleObjectProperty<Date> bDate;

    private final SimpleObjectProperty<Date> lastPasswordChange;

    private final SimpleObjectProperty<Byte[]> photo;

    private final SimpleObjectProperty<Company> company;

    public User() {
        this.id = new SimpleIntegerProperty();
        this.login = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.fullname = new SimpleStringProperty();
        this.status = new SimpleObjectProperty();
        this.privilege = new SimpleObjectProperty();
        this.password = new SimpleStringProperty();
        this.documents = new SimpleListProperty();
        this.lastAccess = new SimpleObjectProperty();
        this.bDate = new SimpleObjectProperty<>();
        this.lastPasswordChange = new SimpleObjectProperty();
        this.photo = new SimpleObjectProperty();
        this.company = new SimpleObjectProperty();
    }

    public User(int id, String login, String email, String fullname, UserStatus status, UserPrivilege privilege, String password, ObservableList<Document> documents, Date lastAccess, Date bDate, Date lastPasswordChange, Byte[] photo, Company company) {
        this.id = new SimpleIntegerProperty(id);;
        this.login = new SimpleStringProperty(login);;
        this.email = new SimpleStringProperty(email);
        this.fullname = new SimpleStringProperty(fullname);
        this.status = new SimpleObjectProperty(status);
        this.privilege = new SimpleObjectProperty(privilege);
        this.password = new SimpleStringProperty(password);
        this.documents = new SimpleListProperty(documents);
        this.lastAccess = new SimpleObjectProperty(lastAccess);
        this.bDate = new SimpleObjectProperty(bDate);
        this.lastPasswordChange = new SimpleObjectProperty(lastPasswordChange);
        this.photo = new SimpleObjectProperty(photo);
        this.company = new SimpleObjectProperty(company);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getLogin() {
        return this.login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.login.set(email);
    }

    public String getFullname() {
        return this.fullname.get();
    }

    public void setFullName(String fullname) {
        this.login.set(fullname);
    }

    public UserStatus getStatus() {
        return this.status.get();
    }

    public void setStatus(UserStatus status) {
        this.status.set(status);
    }

    public UserPrivilege getPrivilege() {
        return this.privilege.get();
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege.set(privilege);
    }

    public String getPassword() {
        return this.password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Collection<Document> getDocuments() {
        return this.documents.get();
    }

    public void setDocuments(Collection<Document> documents) {
        this.documents.setAll(documents);
    }

    public Date getLastAccess() {
        return this.lastAccess.get();
    }
    
    public void setLastAccess(Date lastAccess){
        this.lastAccess.set(lastAccess);
    }
    
    public Date getBDate() {
        return this.bDate.get();
    }
    
    public void setBDate(Date bDate){
        this.bDate.set(bDate);
    }

    public Date getLastPasswordChange() {
        return this.lastPasswordChange.get();
    }

    public void setLastPasswordChange(Date lastPasswordChange){
        this.lastPasswordChange.set(lastPasswordChange);
    }
    
    public Byte[] getPhoto() {
        return this.photo.get();
    }
    
    public void setPhoto(Byte[] photo) {
        this.photo.set(photo);
    }

    public Company getCompany() {
        return this.company.get();
    }
    
    public void setCompany(Company company) {
        this.company.set(company);
    }

}
