package nl.deholtmans.unidirection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UnidirChild
{
    private Long id;
    private String childName;
    private UnidirParent parent;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getChildName() {
        return childName;
    }
    public void setChildName(String childName) {
        this.childName = childName;
    }

    // DON"T put the OneToMany in the parent
    @ManyToOne(cascade=CascadeType.ALL)
    public UnidirParent getParent() {
        return parent;
    }
    public void setParent(UnidirParent parent) {
        this.parent = parent;
    }
}