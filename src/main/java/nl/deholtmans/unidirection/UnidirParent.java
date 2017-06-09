package nl.deholtmans.unidirection;

/*
  To map a unidirectional One-to-Many relationship, you only need to use the @ManyToOne annotation at the Child
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UnidirParent
{
    private Long id;
    private String parentName;
    // DON"T put the OneToMany in the parent

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }
}
