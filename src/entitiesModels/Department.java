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
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final SimpleIntegerProperty id;
    
    private final SimpleStringProperty name;
    
    private final SimpleListProperty<Area> areas;
    
     private SimpleListProperty<Department> companies;
     
    public Department() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.areas = new SimpleListProperty();
        this.companies= new SimpleListProperty();
    }

    public Department(int id, String name, ObservableList<Company> companies, ObservableList<Area> areas) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.areas = new SimpleListProperty(areas);
        this.companies= new SimpleListProperty(companies);
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

    public Collection<Area> getArea() {
        return this.areas.get();
    }

    public void setArea(Collection<Area> areas) {
        this.areas.setAll(areas);
    }

    /**
     * @param companies the companies to set
     */
    public void setCompanies(Collection<Department> companies) {
        this.companies.setAll(companies);
    }

}