package task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// @Component marks a class as being a candidate for auto-detection when using
// annotation-based configuration and classpath scanning.
@Component
public class ScheduledTask {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	// Scheduled annotation defines when a particular method runs. FixedRate specifies
	// the interval between start time of method invocations; other options are fixedDelay
	// (interval between invocations measured from the completion of the task) and cron
	// expressions.
	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		System.out.println("The current time is " + dateFormat.format(new Date()));
	}
}
