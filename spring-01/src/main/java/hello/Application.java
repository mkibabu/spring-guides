package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Runs the application as a jar, rather than a war, using Spring's support for
 * embedding the Tomcat servlet container as the HTTP runtime, rather than
 * deploying the app to an external instance.
 *
 */

/*
 * The SpringBootApplication annotation wraps the following annotations:
 * 1. @Configuration - tags the class as a source of bean definitions for the
 * application context.
 * 2. @EnableAutoConfiguration - tells Spring to start adding beans based on
 * classpath settings, other beans, and various property settings
 * 3. @EnableWebMvc - Flags and activates the application as a web application,
 * e.g. by setting up a DispatcherServlet
 * 4. @ComponentScan - tells Spring Boot to look for components, configurations
 * and services in the package, allowing it to find the controller.
 */
@SpringBootApplication
public class Application {

    public static void main (String[] args) {
        SpringApplication.run(Application.class,  args);
    }
}
