package nl.deholtmans.messages_link;

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
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
// Werkt niet: @ImportResource({"classpath:data_context.xml"})
// Mag ook: @RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:data_context.xml"})
@Transactional
public class Hibernate_Session_LinkedMessages_Test {
    static final Logger logger = LogManager.getLogger(Hibernate_Session_LinkedMessages_Test.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testBasicSessionCalls() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for( int i = 0; i < 3; i++) {
            Message message = new Message("Hello World " + i);
            session.save(message);
        }
        tx.commit();
        session.close();

        // Second unit of work
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List messages = session.createQuery("from Message m order by m.text asc").list();
        System.out.println( messages.size() + " message(s) found:" );
        long id = 0;
        for (Iterator iter = messages.iterator(); iter.hasNext(); ) {
            Message loadedMsg = (Message) iter.next();
            System.out.println( loadedMsg.getText() );
            id = loadedMsg.getId();
        }
        tx.commit();
        session.close();

        // Third unit of work
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        Message loadedMessage = (Message) session.get( Message.class,id);
        for( int i = 0; i < 5; i++) {
            Message newMessage = new Message("Numbering " + i);
            loadedMessage.setNextMessage(newMessage);
            loadedMessage = newMessage;
        }
        tx.commit();
        session.close();

        // Final unit of work (just repeat the query)
        int i = 5;
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        loadedMessage = (Message) session.get( Message.class,id);
        while( loadedMessage != null && i-- > 0) {
            System.out.println( loadedMessage.getText() );
            loadedMessage = loadedMessage.getNextMessage();
        }
        tx.commit();
        session.close();


        // Shutting down the application
        sessionFactory.close();

    }
}
