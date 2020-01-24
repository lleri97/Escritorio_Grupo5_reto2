/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesModels;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ruben
 */
@XmlRootElement(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    private final SimpleIntegerProperty id;

    private final SimpleStringProperty name;

    private final SimpleStringProperty cif;

    private Set<Department> departments;

    public Company() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.cif = new SimpleStringProperty();
    }

    public Company(int id, String name, String cif, Set<Department> departments) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.cif = new SimpleStringProperty(cif);
        this.departments = departments;
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCif() {
        return this.cif.get();
    }

    public void setCif(String cif) {
        this.cif.set(cif);
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return name.get();
    }
}