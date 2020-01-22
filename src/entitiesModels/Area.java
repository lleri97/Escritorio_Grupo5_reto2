/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesModels;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Andoni
 */
@XmlRootElement(name="area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final SimpleIntegerProperty id;
    
    private final SimpleStringProperty name;
    
    private Set<Document> documents;

    public Area() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    public Area(int id, String name, Set<Document> documents) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.documents = documents;
    }

    public Integer getId() {
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


    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    
    
}