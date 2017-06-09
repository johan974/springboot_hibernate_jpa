package nl.deholtmans.messages_link;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.*;

@RestController
@ImportResource({"classpath:data_context.xml"})
@RequestMapping("/employees")
public class RestServiceController {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    static final Logger logger = LogManager.getLogger(RestServiceController.class.getName());

    // GET all
    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getAll() {
        logger.info( "Getting all employee: ");
        Session session = sessionFactory.openSession();
        List<Employee> employees = session.createCriteria( Employee.class).list();
        for( Employee employee : employees) {
            logger.info( "Emp: " + employee);
        }
        session.close();
        return employees;
    }

    // GET
    @RequestMapping( value = "/id/{id}", produces = "application/json")
    public @ResponseBody Employee getEmployee( @PathVariable String id) {
        Employee employee;
        logger.info( "Getting employee: " + id);
        try {
            long idx = Long.parseLong( id);
            Session session = sessionFactory.openSession();
            employee = (Employee)session.load(Employee.class, idx);
            logger.info( "The employee found is: " + employee);
            session.close();
            return employee;
        } catch (Exception e) {
            logger.error( e.getMessage());
            return null;
        }
    }

    // GET
    @RequestMapping( value = "/name/{name}", produces = "application/json")
    public @ResponseBody Employee getEmployeeByName( @PathVariable String name) {
        Employee employee;
        logger.info( "Getting employee: " + name);
        try {
            Session session = sessionFactory.openSession();
            Criteria query = session.createCriteria( Employee.class);
            query.add(Restrictions.eq( "name", name));
            List<Employee> employees = query.list();
            session.close();
            if( employees == null || employees.isEmpty()) {
                return null;
            }
            logger.info( "The first employee found is: " + employees.get( 0));
            return employees.get( 0);
        } catch (Exception e) {
            logger.error( e.getMessage());
            return null;
        }
    }

    // Create via POST
    @RequestMapping( method=RequestMethod.POST)
    @ResponseBody
    public Employee createEmployee( @RequestBody Employee employee) {
        Transaction tr;
        Session session = null;
        logger.info( "Creating employee: " + employee);
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            session.save( employee);
            tr.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            if( session != null) {  session.close(); }
        }
        logger.info( "Creating employee OK: " + employee);
        return employee;
    }

    // Update via PUT
    @RequestMapping( method=RequestMethod.PUT)
    @ResponseBody
    public Employee updateEmployee( @RequestBody Employee employee) {
        Transaction tr = null;
        Session session = null;
        logger.info( "Update employee: " + employee);
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            session.merge( employee);
            tr.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            tr.rollback();
            return null;
        } finally {
            if( session != null) {  session.close(); }
        }
        logger.info( "Update employee OK: " + employee);
        return employee;
    }

    // Delete via Delete
    @RequestMapping( method=RequestMethod.DELETE, value="/{id}")
    public void deleteEmployee(@PathVariable String id) {
        Transaction tr = null;
        Session session = null;
        logger.info( "Deleting employee: " + id);
        try {
            long idx = Long.parseLong( id);
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            Employee emp = (Employee)session.load(Employee.class, idx);
            session.delete(emp);
            tr.commit();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info( "Deleting employee OK: " + id);
    }
}