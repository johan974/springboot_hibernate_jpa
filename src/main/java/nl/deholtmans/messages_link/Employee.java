package nl.deholtmans.messages_link;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "employeesboot")
*/
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private float salary;
    private String designation;

    public Employee() { }

    public Employee( String name, float salary, String designation) {
        this.name = name;
        this.salary = salary;
        this.designation = designation;
    }
    public Employee( long id, String name, float salary, String designation) {
        this( name, salary, designation);
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }
    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return String.format("Employee: id=%d, name='%s', salary='%.2f', designation=%s]", id, name, salary, designation);
    }
}