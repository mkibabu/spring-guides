package receiver;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.util.FileSystemUtils;

/**
 * Message-driven POJO.
 */

public class Receiver {

    // Get a copy of the application cntext
    @Autowired
    ConfigurableApplicationContext context;
    
    /*
     * Print a received message, then shut down the application. Finally, clean
     * up the ActiveMQ server.
     * JmsListener annotation defines the name of the "Destination" that this
     * method should listen to.
     */
    @JmsListener(destination="mailbox-destination", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        context.close();
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }
}
