package nl.deholtmans.bidirectional;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
public class BidirParent
{
    private Long id;
    private String parentName;
    private Set<BidirChild> children;

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
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    // The mappedBy property is what we use to tell Hibernate which variable we are
    //     using to represent the parent class in our child class.
    @OneToMany(cascade=CascadeType.ALL, mappedBy="parent")
    public Set<BidirChild> getChildren() {
        return children;
    }
    public void setChildren( Set<BidirChild> children) {
        this.children = children;
    }
    public void addChild( BidirChild child) {
        if( children == null) {
            children = new HashSet<BidirChild>();
        }
        children.add( child);
    }
}