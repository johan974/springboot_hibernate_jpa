package nl.deholtmans.messages_link;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:data_context.xml"})
public class Application {
 
    static final Logger logger = LogManager.getLogger(Application.class.getName());
 
    public static void main( String[] args) {
        logger.info("Entering the Spring boot employee application");
        SpringApplication.run( Application.class, args);
    }
}