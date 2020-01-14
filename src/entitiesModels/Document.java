/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesModels;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ruben
 */
@XmlRootElement
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    private final SimpleIntegerProperty id;

    private final SimpleStringProperty name;

    private final SimpleStringProperty description;

    private final SimpleObjectProperty<User> user;

    private final SimpleListProperty<Area> areas;

    private final SimpleBooleanProperty visibility;

    private final SimpleObjectProperty<DocumentStatus> status;

    private final SimpleObjectProperty<Byte[]> documentContent;

    private final SimpleObjectProperty<Date> uploadDate;

    public Document() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.user = new SimpleObjectProperty();
        this.areas = new SimpleListProperty();
        this.visibility = new SimpleBooleanProperty();
        this.status = new SimpleObjectProperty();
        this.documentContent = new SimpleObjectProperty();
        this.uploadDate = new SimpleObjectProperty();
    }
    
    public Document(int id, String name, String description, User user, ObservableList<Area> areas, Boolean visibility, DocumentStatus status, Byte[] documentContent, Date uploadDate) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.user = new SimpleObjectProperty(user);
        this.areas = new SimpleListProperty(areas);
        this.visibility = new SimpleBooleanProperty(visibility);
        this.status = new SimpleObjectProperty(status);
        this.documentContent = new SimpleObjectProperty(documentContent);
        this.uploadDate = new SimpleObjectProperty(uploadDate);
    }

    public int getId() {
        return this.id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }

    public String getName() {
        return this.name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return this.description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
    
    public User getUser() {
        return this.user.get();
    }
    
    public void setUser(User user) {
        this.user.set(user);
    }

    public Collection<Area> getAreas() {
        return this.areas.get();
    }
    
    public void setAreas(Collection<Area> areas) {
        this.areas.setAll(areas);
    }

    public boolean getVisibility() {
        return this.visibility.get();
    }
    
    public void setVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }

    public DocumentStatus getStatus() {
        return this.status.get();
    }
    
    public void setStatus(DocumentStatus status) {
        this.status.set(status);
    }

    public Byte[] getDocumentContent() {
        return this.documentContent.get();
    }
    
    public void setDocumentContent(Byte[] documentContent) {
        this.documentContent.set(documentContent);
    }

    public Date getUploadDate() {
        return this.uploadDate.get();
    }
    
    public void setUploadDate(Date uploadDate) {
        this.uploadDate.set(uploadDate);
    }

    

}
