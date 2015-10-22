package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles GET requests. *
 */

/*
 * The RestController annotation marks this class as a controller where every
 * method returns a domain object rather than a view. It combines the behavior
 * of the Controller and ResponseBody annotations. ResponseBody indicates that
 * the return type of a method (or a controller's methods) should be written to
 * the HTTP response & not be placed in a Model, or interpreted as a view name.
 */
@RestController
public class GreetingController {

	private static final String TEMPLATE = "Hello, %s";
	private final AtomicLong counter = new AtomicLong();
	
	/**
	 * Handles requests to /greeting.
	 * @param name the name argument
	 * @return a Greeting with the name and greeting.
	 */
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
	}

	/*
	 * The RequestMapping annotation ensures that HTTP requests to /greeting
	 * are handled by this method. The annotation maps all HTTP operations by
	 * default, i.e. both GET and POST requests will be handled by this method.
	 * To narrow it down, specify a "method" attribute to the annotation.
	 * The RequestParam annotation binds the value of the query string param
	 * "name" to the "name" argument of the greeting() method, and specifies
	 * a default value if the argument is missing.
	 */
}
