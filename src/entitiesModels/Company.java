/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesModels;

import java.io.Serializable;
import java.util.Collection;
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

    private final SimpleListProperty<User> users;

    private final SimpleListProperty<Department> departments;

    public Company(int id, String name, String cif, ObservableList<User> users, ObservableList<Department> departments) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.cif = new SimpleStringProperty(cif);
        this.users = new SimpleListProperty(users);
        this.departments = new SimpleListProperty(departments);
    }

    public Company() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.cif = new SimpleStringProperty();
        this.users = new SimpleListProperty();
        this.departments = new SimpleListProperty();
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

    public Collection<User> getUsers() {
        return this.users.get();
    }

    public void setUsers(Collection<User> users) {
        this.users.setAll(users);
    }

    public Collection<Department> getDepartments() {
        return this.departments.get();
    }

    public void setDepartments(Collection<Department> departments) {
        this.departments.setAll(departments);
    }

}