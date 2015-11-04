package receiver;

import java.util.concurrent.CountDownLatch;

/*
 * Simple POJO that defines a method for receiving messages.
 */

public class Receiver {

    // CountDownLatch allows it to signal that a message has been received. Not
    // likely to be implemented in a production environment.
    private CountDownLatch latch = new CountDownLatch(1);
    
    public void receiveMessage(String message) {
        System.out.println("Received <" + message);
        latch.countDown();
    }
    
    public CountDownLatch getLatch() {
        return latch;
    }
}
