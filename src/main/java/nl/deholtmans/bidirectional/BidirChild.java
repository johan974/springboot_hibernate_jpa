package nl.deholtmans.bidirectional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BidirChild
{
    private Long id;
    private String childName;
    private BidirParent parent;

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
    @ManyToOne(cascade=CascadeType.ALL)
    public BidirParent getParent() {
        return parent;
    }
    public void setParent(BidirParent parent) {
        this.parent = parent;
    }
}