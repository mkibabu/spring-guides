package receiver;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Simple POJO that defines a method for receiving messages.
 */
public class Receiver {

	private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);
	
	private CountDownLatch latch;
	
	@Autowired
	public Receiver(CountDownLatch latch) {
		this.latch = latch;
	}
	
	public void receiveMessage(String message) {
		LOG.info("Received <" + message + ">");
		latch.countDown();
	}
	
}
