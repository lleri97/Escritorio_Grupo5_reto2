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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ruben
 */
@XmlRootElement
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private final SimpleStringProperty name;

    private final SimpleStringProperty description;

    private Boolean visibility;

    private DocumentStatus status;

    private byte[] documentContent;

    private final SimpleObjectProperty<Date> uploadDate;

    public Document() {
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.uploadDate = new SimpleObjectProperty();
    }

    public Document(int id, String name, String description, Boolean visibility, DocumentStatus status, byte[] documentContent, Date uploadDate) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);

        this.visibility = visibility;
        this.status = status;
        this.documentContent = documentContent;
        this.uploadDate = new SimpleObjectProperty(uploadDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public byte[] getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(byte[] documentContent) {
        this.documentContent = documentContent;
    }

    public Date getUploadDate() {
        return this.uploadDate.get();
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate.set(uploadDate);
    }

}
