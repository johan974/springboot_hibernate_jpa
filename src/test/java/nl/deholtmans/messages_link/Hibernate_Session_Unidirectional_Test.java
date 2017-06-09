package nl.deholtmans.messages_link;

import nl.deholtmans.unidirection.UnidirChild;
import nl.deholtmans.unidirection.UnidirParent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
// Werkt niet: @ImportResource({"classpath:data_context.xml"})
// Mag ook: @RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:data_context.xml"})
@Transactional
public class Hibernate_Session_Unidirectional_Test {
    static final Logger logger = LogManager.getLogger(Hibernate_Session_Unidirectional_Test.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testBasicSessionCalls() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for( int i = 0; i < 2; i++) {
            UnidirParent parent = new UnidirParent();
            parent.setParentName( "Parent " + i);
            session.save(parent);
            for( int j = 0; j < 3; j++) {
                UnidirChild child = new UnidirChild();
                child.setChildName( "Parent " + i + ", child " + j);
                child.setParent( parent);
                session.save( child);
            }
        }
        tx.commit();
        session.close();

        // Second unit of work
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List<UnidirChild> children = session.createQuery("from UnidirChild").list();
        for( UnidirChild child : children) {
            UnidirParent pa = child.getParent();
            System.out.println( "Child = " + child.getChildName() + " with parent = " + pa.getParentName());
        }
        tx.commit();
        session.close();

        // Shutting down the application
        sessionFactory.close();

    }
}
