/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesModels;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ruben
 */
@XmlRootElement
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    private final SimpleIntegerProperty id;

    private final SimpleStringProperty name;

    private final SimpleStringProperty cif;


    private final Set <Department> departments;

    public Company(int id, String name, String cif, Set<Department> departments) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.cif = new SimpleStringProperty(cif);
        this.departments = new HashSet<>(departments);
    }

    public Company() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.cif = new SimpleStringProperty();
        this.departments = new HashSet<>();
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif.set(cif);
    }

    public Set<Department> getDepartments() {
        return this.departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments.addAll(departments);
    }

}